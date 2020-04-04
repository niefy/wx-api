package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.WxUserMapper;
import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.dto.PageSizeConstant;
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
    private WxMpService wxService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String openid = (String) params.get("openid");
        String nickname = (String) params.get("nickname");
        IPage<WxUser> page = this.page(
            new Query<WxUser>().getPage(params),
            new QueryWrapper<WxUser>()
                .eq(!StringUtils.isEmpty(openid), "openid", openid)
                .like(!StringUtils.isEmpty(nickname), "nickname", nickname)
        );

        return new PageUtils(page);
    }

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public WxUser refreshUserInfo(String openid) {

        try {
            // 获取微信用户基本信息
            WxMpUser userWxInfo = wxService.getUserService().userInfo(openid, null);
            if (userWxInfo != null) {
                WxUser user = new WxUser(userWxInfo);
                this.updateOrInsert(user);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计数
     *
     * @return
     */
    @Override
    public int count() {
        return userMapper.selectCount(new QueryWrapper<>());
    }

    /**
     * 检查用户是否关注微信，已关注时会保存用户的信息
     *
     * @param openid
     * @return
     */
    @Override
    public boolean wxSubscribeCheck(String openid) {
        try {
            // 获取微信用户基本信息
            WxMpUser userWxInfo = wxService.getUserService().userInfo(openid, null);
            if (userWxInfo != null && userWxInfo.getSubscribe()) {
                WxUser user = new WxUser(userWxInfo);
                this.updateOrInsert(user);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    @Override
    public void updateOrInsert(WxUser user) {
        Integer updateCount = userMapper.updateById(user);
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
    public void syncWxUsers() {
        boolean hasMore=true;
        String nextOpenid=null;
        WxMpUserService wxMpUserService = wxService.getUserService();
        try {
            while (hasMore){
                WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);
                List<WxMpUser> wxMpUsers = wxMpUserService.userInfoList(wxMpUserList.getOpenids());
                List<WxUser> wxUsers=wxMpUsers.parallelStream().map(WxUser::new).collect(Collectors.toList());
                this.saveOrUpdateBatch(wxUsers);
                nextOpenid=wxMpUserList.getNextOpenid();
                hasMore=StringUtils.isEmpty(nextOpenid);
            }
        } catch (WxErrorException e) {
            logger.error("同步公众号粉丝出错:",e);
        }

    }
}
