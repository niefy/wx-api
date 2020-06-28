package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.WxMsg;

import java.util.Map;

/**
 * 微信消息
 *
 * @author niefy
 * @date 2020-05-14 17:28:34
 */
public interface WxMsgService extends IService<WxMsg> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 记录msg，异步入库
     * @param msg
     */
    void addWxMsg(WxMsg msg);
}

