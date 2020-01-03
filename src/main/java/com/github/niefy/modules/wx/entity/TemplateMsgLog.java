package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.github.niefy.common.utils.Json;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nifury
 * @date 2017-9-27
 */
@TableName("template_msg_log")
public class TemplateMsgLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private int logId;
    private String touser;
    private String templateId;
    private String url;
    private String data;
    private String color;
    private Date sendTime;
    private String sendResult;

    public TemplateMsgLog() {
    }

    public TemplateMsgLog(WxMpTemplateMessage msg, String sendResult) {
        this.touser = msg.getToUser();
        this.templateId = msg.getTemplateId();
        this.url = msg.getUrl();
        this.data = msg.toJson();
        this.sendTime = new Date(System.currentTimeMillis());
        this.sendResult = sendResult;
    }

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

}