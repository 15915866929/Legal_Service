package com.core.base.support;

import java.io.Serializable;

/**
 * 查询参数
 *
 * @author 梁湛桐
 */
public class Parameter implements Serializable {
    private static final long serialVersionUID = 6677889568241104817L;
    /**
     * 参数名
     */
    private String key;
    /**
     * 参数值
     */
    private Object value;
    
    private Parameter(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    /**
     * 新建查询参数
     *
     * @param key
     * @param value
     * @return
     */
    public static Parameter create(String key, Object value) {
        return new Parameter(key, value);
    }
    
    /**
     * 参数名
     *
     * @return
     */
    public String getKey() {
        return this.key;
    }
    
    /**
     * 参数值
     *
     * @return
     */
    public Object getValue() {
        return this.value;
    }
    
}
