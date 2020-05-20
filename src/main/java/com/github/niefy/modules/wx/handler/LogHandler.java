package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.common.utils.Json;

import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class LogHandler extends AbstractHandler {
    @Autowired
    WxMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        try {
            this.logger.debug("\n接收到请求消息，内容：{}", Json.toJsonString(wxMessage));
            wxMsgService.addWxMsg(new WxMsg(wxMessage));
        } catch (Exception e) {
            this.logger.error("记录消息异常",e);
        }

        return null;
    }

}
