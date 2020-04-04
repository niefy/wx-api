package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    User refreshUserInfo(String openid);

    /**
     * 分页查询用户数据，每页50条数据
     *
     * @param pageNumber
     * @return
     */
    List<User> getUserList(int pageNumber, String nickname);

    /**
     * 计数
     *
     * @return
     */
    int count();

    /**
     * 检查用户是否关注微信，已关注时会保存用户的信息
     *
     * @param openid
     * @return
     */
    boolean wxSubscribeCheck(String openid);

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    void updateOrInsert(User user);

    /**
     * 取消关注，更新关注状态
     *
     * @param openid
     */
    void unsubscribe(String openid);
}
