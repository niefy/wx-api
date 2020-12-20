package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.Query;
import com.github.niefy.config.TaskExcutor;
import com.github.niefy.modules.wx.dao.WxUserMapper;
import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.service.WxUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nifury
 * @date 2017-9-27
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxUserMapper userMapper;
	@Autowired
	private WxMpService wxMpService;
    private volatile static  boolean syncWxUserTaskRunning=false;

    @Override
    public IPage<WxUser> queryPage(Map<String, Object> params) {
        String openid = (String) params.get("openid");
        String nickname = (String) params.get("nickname");
		String appid = (String) params.get("appid");
		String city = (String) params.get("city");
		String qrSceneStr = (String) params.get("qrSceneStr");
        return this.page(
            new Query<WxUser>().getPage(params),
            new QueryWrapper<WxUser>()
				.eq(StringUtils.hasText(appid), "appid", appid)
                .eq(StringUtils.hasText(openid), "openid", openid)
                .like(StringUtils.hasText(nickname), "nickname", nickname)
				.eq(StringUtils.hasText(city), "city", city)
				.eq(StringUtils.hasText(qrSceneStr), "qrSceneStr", qrSceneStr)
        );
    }

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public WxUser refreshUserInfo(String openid,String appid) {
        try {
			// 获取微信用户基本信息
			logger.info("更新用户信息，openid={}",openid);
			wxMpService.switchover(appid);
			WxMpUser userWxInfo = wxMpService.getUserService().userInfo(openid, null);
			if (userWxInfo == null) {
				logger.error("获取不到用户信息，无法更新,openid:{}",openid);
				return null;
			}
			WxUser user = new WxUser(userWxInfo,appid);
			this.saveOrUpdate(user);
			return user;
		} catch (Exception e) {
			logger.error("更新用户信息失败,openid:{}",openid);
		}
		return null;
    }

    /**
	 * 异步批量同步用户信息
	 * @param openidList
	 */
	@Override
	@Async
	public void refreshUserInfoAsync(String[] openidList,String appid) {
		logger.info("批量更新用户信息：任务开始");
		for(String openid:openidList){
			wxMpService.switchover(appid);
			TaskExcutor.submit(()->this.refreshUserInfo(openid,appid));
		}
		logger.info("批量更新用户信息：任务全部添加到线程池");
	}

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    @Override
    public void updateOrInsert(WxUser user) {
        int updateCount = userMapper.updateById(user);
        if (updateCount < 1) {
            userMapper.insert(user);
        }
    }

    @Override
    public void unsubscribe(String openid) {
        userMapper.unsubscribe(openid);
    }
    
    /**
	 * 同步用户列表,公众号一次拉取调用最多拉取10000个关注者的OpenID，可以通过传入nextOpenid参数多次拉取
	 */
    @Override
	@Async
    public void syncWxUsers(String appid) {
		//同步较慢，防止个多线程重复执行同步任务
		Assert.isTrue(!syncWxUserTaskRunning,"后台有同步任务正在进行中，请稍后重试");
		wxMpService.switchoverTo(appid);
		syncWxUserTaskRunning=true;
		logger.info("同步公众号粉丝列表：任务开始");
		wxMpService.switchover(appid);
		boolean hasMore=true;
		String nextOpenid=null;
		WxMpUserService wxMpUserService = wxMpService.getUserService();
		try {
			int page=1;
			while (hasMore){
				WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);//拉取openid列表，每次最多1万个
				logger.info("拉取openid列表：第{}页，数量：{}",page++,wxMpUserList.getCount());
				List<String> openids = wxMpUserList.getOpenids();
				this.syncWxUsers(openids,appid);
				nextOpenid=wxMpUserList.getNextOpenid();
				hasMore=StringUtils.hasText(nextOpenid) && wxMpUserList.getCount()>=10000;
			}
		} catch (WxErrorException e) {
			logger.error("同步公众号粉丝出错:",e);
		}finally {
			syncWxUserTaskRunning=false;
		}
		logger.info("同步公众号粉丝列表：完成");
	}

	/**
	 * 通过传入的openid列表，同步用户列表
	 * @param openids
	 */
	@Override
	public void syncWxUsers(List<String> openids,String appid) {
		if(openids.size()<1) {
            return;
        }
		final String batch=openids.get(0).substring(20);//截取首个openid的一部分做批次号（打印日志时使用，无实际意义）
		WxMpUserService wxMpUserService = wxMpService.getUserService();
		int start=0,batchSize=openids.size(),end=Math.min(100,batchSize);
		logger.info("开始处理批次：{}，批次数量：{}",batch,batchSize);
		while (start<end && end<=batchSize){//分批处理,每次最多拉取100个用户信息
			final int finalStart = start,finalEnd = end;
			final List<String> subOpenids=openids.subList(finalStart,finalEnd);
			TaskExcutor.submit(()->{//使用线程池同步数据，否则大量粉丝数据需同步时会很慢
				logger.info("同步批次:【{}--{}-{}】，数量：{}",batch, finalStart, finalEnd,subOpenids.size());
				wxMpService.switchover(appid);
				List<WxMpUser> wxMpUsers = null;//批量获取用户信息，每次最多100个
				try {
					wxMpUsers = wxMpUserService.userInfoList(subOpenids);
				} catch (WxErrorException e) {
					logger.error("同步出错，批次：【{}--{}-{}】，错误信息：{}",batch, finalStart, finalEnd,e);
				}
				if(wxMpUsers!=null && !wxMpUsers.isEmpty()){
					List<WxUser> wxUsers=wxMpUsers.parallelStream().map(item->new WxUser(item,appid)).collect(Collectors.toList());
					this.saveOrUpdateBatch(wxUsers);
				}
			});
			start=end;
			end=Math.min(end+100,openids.size());
		}
		logger.info("批次：{}处理完成",batch);
	}

}
