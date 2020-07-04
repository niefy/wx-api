package com.github.niefy.modules.wx.enums;

/**
 * 定义文章类型
 */
public enum ArticleTypeEnum {
    /**
     * 普通文章
     */
    COMMON(1),
    /**
     * 帮助中心文章
     */
    QUESTION(5);
    /**
     * 数据库属性值
     */
    private int value;

    ArticleTypeEnum(int type) {
        this.value = type;
    }

    public int getValue() {
        return this.value;
    }

    public static ArticleTypeEnum of(String name) {
        try {
            return ArticleTypeEnum.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
