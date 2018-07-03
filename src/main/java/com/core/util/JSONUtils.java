package com.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具类
 *
 * @author lzt
 */
public final class JSONUtils {
    /**
     * 单例
     */
    private JSONUtils() {
    }
    
    /**
     * bean 转 json
     *
     * @param bean 要转换的bean
     * @return json字符串
     */
    public static String beanToJson(Object bean) {
        return JSONUtils.beanToJson(bean, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * bean 转 json
     *
     * @param bean       要转换的bean
     * @param dateFormat json字符串的日期字段样式
     * @return json字符串
     */
    public static String beanToJson(Object bean, String dateFormat) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        return gson.toJson(bean);
    }
    
    /**
     * json字符串转对象
     *
     * @param json  json字符串
     * @param clazz bean的类型
     * @return bean对象
     */
    public static <E> E jsonToBean(String json, Class<E> clazz) {
        return JSONUtils.jsonToBean(json, "yyyy-MM-dd HH:mm:ss", clazz);
    }
    
    /**
     * json字符串转对象
     *
     * @param json       json字符串
     * @param dateFormat json字符串的日期字段样式
     * @param clazz      bean的类型
     * @return
     */
    public static <E> E jsonToBean(String json, String dateFormat, Class<E> clazz) {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        return gson.fromJson(json, clazz);
    }
    
}
