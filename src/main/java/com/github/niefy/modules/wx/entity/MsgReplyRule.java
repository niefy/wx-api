package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.github.niefy.common.utils.Json;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Time;

/**
 * @author Nifury
 * @date 2017-11-1
 */
@TableName("msg_reply_rule")
public class MsgReplyRule implements Serializable {

    @TableId(type = IdType.AUTO)
    private int ruleId;
    @NotEmpty(message = "规则名称不得为空")
    private String ruleName;
    @NotEmpty(message = "匹配关键词不得为空")
    private String matchValue;
    private String replyType;
    @NotEmpty(message = "回复内容不得为空")
    private String replyContent;
    @TableField(value = "`status`")
    private int status;
    @TableField(value = "`desc`")
    private String desc;
    private Time effectTimeStart;
    private Time effectTimeEnd;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Time getEffectTimeStart() {
        return effectTimeStart;
    }

    public void setEffectTimeStart(Time effectTimeStart) {
        this.effectTimeStart = effectTimeStart;
    }

    public Time getEffectTimeEnd() {
        return effectTimeEnd;
    }

    public void setEffectTimeEnd(Time effectTimeEnd) {
        this.effectTimeEnd = effectTimeEnd;
    }
}