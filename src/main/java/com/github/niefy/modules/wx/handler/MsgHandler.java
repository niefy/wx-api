package com.github.niefy.modules.wx.handler;


import java.util.Map;

import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxMsgService;
import me.chanjar.weixin.common.api.WxConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MsgHandler extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MsgReplyService msgReplyService;
    @Autowired
    WxMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        String textContent = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        boolean autoReplyed = msgReplyService.tryAutoReply(false, fromUser, textContent);
        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if ("人工".equals(textContent) || !autoReplyed) {
            wxMsgService.addWxMsg(WxMsg.buildOutMsg(WxConsts.KefuMsgType.TRANSFER_CUSTOMER_SERVICE,fromUser,null));
            return WxMpXmlOutMessage
                .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                .toUser(fromUser).build();
        }
        return null;

    }

}
