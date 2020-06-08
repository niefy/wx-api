package com.github.niefy.modules.wx.form;

import com.github.niefy.common.utils.Json;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class WxH5OuthrizeForm {
    @NotEmpty(message = "code不得为空")
    private String code;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
