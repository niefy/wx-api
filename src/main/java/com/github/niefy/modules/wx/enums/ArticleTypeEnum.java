package com.github.niefy.modules.wx.enums;

/**
 * 定义文章类型
 */
public enum ArticleTypeEnum {
    COMMON(1), POPUP(2), NOTICE(3), IMAGE(4), QUESTION(5);
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
