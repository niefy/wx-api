package com.github.niefy.modules.wx.handler;

import com.github.niefy.modules.wx.service.MsgReplyService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class ScanHandler extends AbstractHandler {
    @Autowired
    MsgReplyService msgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) {
        //扫码事件处理
        this.logger.info("用户扫描带参二维码 OPENID: " + wxMpXmlMessage.getFromUser());
        String appid = WxMpConfigStorageHolder.get();
        msgReplyService.tryAutoReply(appid, true, wxMpXmlMessage.getFromUser(), wxMpXmlMessage.getEventKey());

        return null;
    }
}
