package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.github.niefy.common.utils.Json;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Nifury
 * @date 2017-11-1
 */
@Data
@TableName("msg_news")
public class MsgNews implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int newsId;
    private String title;
    private String description;
    private String picUrl;
    private String url;
    @TableField(value = "`status`")
    private int status;
    @TableField(value = "`order`")
    private int order = 0;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }

}
