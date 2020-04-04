package com.github.niefy.modules.wx.dto;

/**
 * 常用正则表达式
 */
public class RegexConstant {
    /**
     * 手机号码
     */
    public static final String PHONE_NUM = "1\\d{10}";
    /**
     * 六位数字
     */
    public static final String SIX_NUMBER = "\\d{6}";
    /**
     * 六位字符
     */
    public static final String SIX_CHAR = ".{6}";
    /**
     * 图片文件名
     */
    public static final String IMAGE_FILE_NAME = ".*\\.(jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG)$";
    /**
     * SQL注入常用字符
     */
    public static final String SQL_INJECTION_WORDS = ".*([';]+|(--)+).*";
    /**
     * 逗号分割的数字列表
     */
    public static final String NUMBER_ARRAY = "^\\d+(,\\d+)*$";
    /**
     * 时间戳，毫秒标识的时间格式
     */
    public static final String TIME_MILLIS = "^[0-9]{10,}$";
    /**
     * 日期字符串格式，如 2018-01-01
     */
    public static final String DATE_STRING = "^\\d{2}-\\d{2}-\\d{2}$";
    /**
     * 时间字符串格式，如 12:00:00
     */
    public static final String TIME_STRING = "^\\d{2}:\\d{2}:\\d{2}$";

}
