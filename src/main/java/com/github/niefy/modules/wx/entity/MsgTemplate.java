package com.github.niefy.modules.wx.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.niefy.common.utils.Json;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板消息模板
 * @author Nifury
 * @date 2017-9-27
 */
@Data
@TableName("wx_msg_template")
public class MsgTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appid;
    private String templateId;
    @TableField(value = "`name`")
    private String name;
    private String title;
    private String content;
    private JSONArray data;
    private String url;
    private JSONObject miniprogram;
    @TableField(value = "`status`")
    private boolean status;
    private Date updateTime;
    public MsgTemplate() {

    }
    public MsgTemplate(WxMpTemplate mpTemplate,String appid) {
        this.appid = appid;
        this.templateId=mpTemplate.getTemplateId();
        this.title=mpTemplate.getTitle();
        this.name=mpTemplate.getTemplateId();
        this.content = mpTemplate.getContent();
        this.status=true;
    }

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

}
