package com.github.niefy.modules.wx.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class WxUserTagForm {
    private Long id;
    @NotEmpty(message = "标签名称不得为空")
    @Size(min = 1,max = 30,message = "标签名称长度必须为1-30字符")
    private String name;
}
