package com.github.niefy.modules.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.niefy.modules.wx.entity.MsgReplyRule;
import com.github.niefy.modules.wx.service.MsgNewsService;
import com.github.niefy.modules.wx.service.MsgReplyRuleService;
import com.github.niefy.modules.wx.service.WeixinMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信公众号消息处理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Ma nagement/Service_Center_messages.html#7
 * 参考WxJava客服消息文档：https://github.com/Wechat-Group/WxJava/wiki/MP_主动发送消息（客服消息）
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeixinMsgServiceImpl implements WeixinMsgService {
    @Autowired
    MsgReplyRuleService msgReplyRuleService;
    @Autowired
    MsgNewsService msgNewsService;
    @Autowired
    WxMpService wxService;

	/**
	 * 根据规则配置通过微信客服消息接口自动回复消息
	 * @param exactMatch 是否精确匹配
	 * @param toUser 用户openid
	 * @param keywords 匹配关键词
	 * @return 是否已自动回复，无匹配规则则不自动回复
	 */
    @Override
    public boolean wxReplyMsg(boolean exactMatch,String toUser, String keywords) {
        try {
            List<MsgReplyRule> rules = msgReplyRuleService.getMatchedRules( exactMatch, keywords);
            if (rules.isEmpty()) return false;
            for (MsgReplyRule rule:rules){
                String replyType = rule.getReplyType();
                String replyContent = rule.getReplyContent();
                if(WxConsts.KefuMsgType.TEXT.equals(replyType))this.replyText(toUser,replyContent);
                else if(WxConsts.KefuMsgType.IMAGE.equals(replyType))this.replyImage(toUser,replyContent);
                else if(WxConsts.KefuMsgType.VOICE.equals(replyType))this.replyVoice(toUser,replyContent);
                else if(WxConsts.KefuMsgType.VIDEO.equals(replyType))this.replyVideo(toUser,replyContent);
                else if(WxConsts.KefuMsgType.MUSIC.equals(replyType))this.replyMusic(toUser,replyContent);
                else if(WxConsts.KefuMsgType.NEWS.equals(replyType))this.replyNews(toUser,replyContent);
                else if(WxConsts.KefuMsgType.MPNEWS.equals(replyType))this.replyMpNews(toUser,replyContent);
                else if(WxConsts.KefuMsgType.WXCARD.equals(replyType))this.replyWxCard(toUser,replyContent);
                else if(WxConsts.KefuMsgType.MINIPROGRAMPAGE.equals(replyType))this.replyMiniProgram(toUser,replyContent);
                else if(WxConsts.KefuMsgType.MSGMENU.equals(replyType))this.replyMsgMenu(toUser,replyContent);
            }
            return true;
        } catch (Exception e) {
            log.error("自动回复出错：", e);
        }
        return false;
    }
    @Override
    public void replyText(String toUser,String content) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(toUser).content(content).build());
    }

    @Override
    public void replyImage(String toUser, String mediaId) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(toUser).mediaId(mediaId).build());
    }

    @Override
    public void replyVoice(String toUser, String mediaId) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.VOICE().toUser(toUser).mediaId(mediaId).build());
    }

    @Override
    public void replyVideo(String toUser, String mediaId) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.VIDEO().toUser(toUser).mediaId(mediaId).build());
    }

    @Override
    public void replyMusic(String toUser, String musicInfoJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(musicInfoJson);
        wxService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MUSIC().toUser(toUser)
                        .musicUrl(json.getString("musicurl"))
                        .hqMusicUrl(json.getString("hqmusicurl"))
                        .title(json.getString("title"))
                        .description(json.getString("description"))
                        .thumbMediaId(json.getString("thumb_media_id"))
                        .build());
    }

    @Override
    public void replyNews(String toUser, String newsIds) throws WxErrorException {
        List<WxMpKefuMessage.WxArticle> newsList = msgNewsService.findByIds(newsIds);
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS().toUser(toUser).articles(newsList).build());
    }

    @Override
    public void replyMpNews(String toUser, String mediaId) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(toUser).mediaId(mediaId).build());
    }

    @Override
    public void replyWxCard(String toUser, String cardId) throws WxErrorException {
        wxService.getKefuService().sendKefuMessage(WxMpKefuMessage.WXCARD().toUser(toUser).cardId(cardId).build());
    }

    @Override
    public void replyMiniProgram(String toUser, String miniProgramInfoJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(miniProgramInfoJson);
        wxService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MINIPROGRAMPAGE()
                        .toUser(toUser)
                        .title(json.getString("title"))
                        .appId(json.getString("appid"))
                        .pagePath(json.getString("pagepath"))
                        .thumbMediaId(json.getString("thumb_media_id"))
                        .build());
    }

    @Override
    public void replyMsgMenu(String toUser, String msgMenusJson) throws WxErrorException {
        JSONObject json = JSON.parseObject(msgMenusJson);
        List<WxMpKefuMessage.MsgMenu> msgMenus = json.getJSONArray("list").toJavaList(WxMpKefuMessage.MsgMenu.class);
        wxService.getKefuService().sendKefuMessage(
                WxMpKefuMessage.MSGMENU()
                        .toUser(toUser)
                        .headContent(json.getString("head_content"))
                        .tailContent(json.getString("tail_content"))
                        .msgMenus(msgMenus).build());
    }

}
