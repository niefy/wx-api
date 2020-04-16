package com.github.niefy.modules.wx.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@Data
public class WxUserBatchTaggingForm {
    @NotNull(message = "标签ID不得为空")
    private Long tagid;
    @NotNull(message = "openid列表不得为空")
    @Length(min = 1,max = 50,message = "每次处理数量1-50个")
    private String[] openidList;
}
