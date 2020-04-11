package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.niefy.common.utils.Json;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nifury
 * @date 2017-9-27
 */
@Data
@TableName("msg_template")
public class MsgTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String templateId;
    private String title;
    private String data;
    private String url;
    private String color;
    @TableField(value = "`status`")
    private boolean status;
    @TableField(value = "`name`")
    private String name;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

}
