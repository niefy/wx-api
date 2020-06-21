package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.niefy.common.utils.Json;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * cms文章
 */
@Data
@TableName("cms_article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;
    private int type;
    @TableField(insertStrategy = FieldStrategy.IGNORED)//title重复则不插入
    @NotEmpty(message = "标题不得为空")
    private String title;
    private String tags;
    private String summary;
    private String content;
    private String category;
    private String subCategory;
    private Date createTime;
    private Date updateTime;
    private int openCount;
    private String targetLink;
    private String image;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
