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
     * 保存消息到数据库（会先添加到队列，再使用定时任务写入）
     * @param log
     */
    void addWxMsg(WxMsg log);
}

