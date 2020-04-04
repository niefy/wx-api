package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.WxUser;

import java.util.List;
import java.util.Map;

public interface WxUserService extends IService<WxUser> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    WxUser refreshUserInfo(String openid);

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
    void updateOrInsert(WxUser user);

    /**
     * 取消关注，更新关注状态
     *
     * @param openid
     */
    void unsubscribe(String openid);
    /**
     * 同步用户列表
     */
    void syncWxUsers();

}
