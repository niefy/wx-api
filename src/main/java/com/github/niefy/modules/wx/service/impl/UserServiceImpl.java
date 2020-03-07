package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.UserMapper;
import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.entity.User;
import com.github.niefy.modules.wx.dto.PageSizeConstant;
import com.github.niefy.modules.wx.service.UserService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Nifury
 * @date 2017-9-27
 */
@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxMpService wxService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String openid = (String)params.get("openid");
		String nickname = (String)params.get("nickname");
		IPage<User> page = this.page(
				new Query<User>().getPage(params),
				new QueryWrapper<User>()
						.eq(!StringUtils.isEmpty(openid),"openid",openid)
						.like(!StringUtils.isEmpty(nickname),"nickname",nickname)
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
	public User refreshUserInfo(String openid) {

		try {
			// 获取微信用户基本信息
			WxMpUser userWxInfo = wxService.getUserService().userInfo(openid, null);
			if (userWxInfo != null) {
				User user = new User(userWxInfo);
				this.updateOrInsert(user);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询用户数据，每页50条数据
	 *
	 * @param pageNumber
	 * @return
	 */
	@Override
	public List<User> getUserList(int pageNumber, String nickname) {
		return userMapper.selectPage(new Page<User>(pageNumber, PageSizeConstant.PAGE_SIZE_SMALL),
				new QueryWrapper<User>().like("nickname", nickname).orderByDesc("subscribe_time")).getRecords();
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
				User user = new User(userWxInfo);
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
	public void updateOrInsert(User user) {
		Integer updateCount = userMapper.updateById(user);
		if (updateCount < 1) {
			userMapper.insert(user);
		}
	}

	@Override
	public void unsubscribe(String openid) {
		userMapper.unsubscribe(openid);
	}
}
