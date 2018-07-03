package com.core.util;

/**
 * 日期格式化
 *
 * @author Yuko
 */
public final class DateTimeFormater {
    /**
     * 日期时间格式化字段
     */
    public static final DateTimeFormater DATETIME = new DateTimeFormater("yyyy-MM-dd HH:mm:ss");
    /**
     * 长日期格式化字段
     */
    public static final DateTimeFormater LONG_DATE = new DateTimeFormater("yyyy-MM-dd");
    /**
     * 短日期格式化字段
     */
    public static final DateTimeFormater SHORT_DATE = new DateTimeFormater("yyyyMMdd");
    /**
     * 中文日期格式化字段
     */
    public static final DateTimeFormater CHINESE_DATE = new DateTimeFormater("yyyy年MM月dd");
    private String pattern;
    
    private DateTimeFormater(String pattern) {
        this.pattern = pattern;
    }
    
    /**
     * 工厂模式获取实例
     *
     * @param pattern
     * @return
     */
    public static DateTimeFormater getInstance(String pattern) {
        return new DateTimeFormater(pattern);
    }
    
    public String getPattern() {
        return this.pattern;
    }
}
