package com.github.niefy.modules.wx.service;

import com.github.niefy.modules.wx.form.TemplateMsgBatchForm;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.scheduling.annotation.Async;

public interface TemplateMsgService {
    /**
     * 发送微信模版消息
     */
    @Async
    void sendTemplateMsg(WxMpTemplateMessage msg,String appid);

    /**
     * 批量消息发送
     * @param form
     * @param appid
     */
    void sendMsgBatch(TemplateMsgBatchForm form,String appid);
}
