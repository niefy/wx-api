package com.github.niefy.modules.wx.service;

import com.github.niefy.modules.wx.form.TemplateMsgForm;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.scheduling.annotation.Async;

public interface TemplateMsgService {
    /**
     * 发送微信模版消息
     */
    @Async
    void sendTemplateMsg(WxMpTemplateMessage msg);

    /**
     * 以默认方式向特定用户发送消息
     *
     * @param form
     */
    void sendToUser(TemplateMsgForm form);
}
