package com.github.niefy.modules.wx.service;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 公众号消息处理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7
 * WxJava客服消息文档：https://github.com/Wechat-Group/WxJava/wiki/MP_主动发送消息（客服消息）
 */
public interface WeixinMsgService {


    /**
     * 根据规则配置通过微信客服消息接口自动回复消息
     * @param exactMatch 是否精确匹配
     * @param toUser 用户openid
     * @param keywords 匹配关键词
     * @return 是否已自动回复，无匹配规则则不自动回复
     */
    boolean wxReplyMsg(boolean exactMatch,String toUser, String keywords);

    /**
     * 回复文字消息
     */
    void replyText(String toUser, String replyContent) throws WxErrorException;
    /**
     * 回复图片消息
     */
    void replyImage(String toUser, String mediaId) throws WxErrorException;
    /**
     * 回复录音消息
     */
    void replyVoice(String toUser, String mediaId) throws WxErrorException;
    /**
     * 回复视频消息
     */
    void replyVideo(String toUser, String mediaId) throws WxErrorException;
    /**
     * 回复音乐消息
     */
    void replyMusic(String toUser, String mediaId) throws WxErrorException;
    /**
     * 回复图文消息
     */
    void replyNews(String toUser, String newsIds) throws WxErrorException;
    /**
     * 回复公众号文章消息
     */
    void replyMpNews(String toUser, String mediaId) throws WxErrorException;
    /**
     * 回复卡券消息
     */
    void replyWxCard(String toUser, String cardId) throws WxErrorException;
    /**
     * 回复小程序消息
     */
    void replyMiniProgram(String toUser, String miniProgramInfoJson) throws WxErrorException;
    /**
     * 回复菜单消息
     */
    void replyMsgMenu(String toUser, String msgMenusJson) throws WxErrorException;
}
