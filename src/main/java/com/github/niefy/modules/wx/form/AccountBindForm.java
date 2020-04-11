package com.github.niefy.modules.wx.form;

import com.github.niefy.common.utils.Json;
import lombok.Data;

@Data
public class AccountBindForm {
    String phoneNum;
    String idCodeSuffix;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
