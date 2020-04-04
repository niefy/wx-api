package com.github.niefy.modules.wx.form;

import com.github.niefy.common.exception.RRException;
import com.github.niefy.common.utils.Json;

public class TemplateMsgForm {
    private String openid;
    private String msg;
    private String template;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

    public boolean isValid() {
        if (openid == null || openid.isEmpty() || msg == null || msg.isEmpty() || template == null || template.isEmpty()) {
            throw new RRException("缺少必要参数");
        }
        return true;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
