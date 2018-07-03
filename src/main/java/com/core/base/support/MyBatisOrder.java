package com.core.base.support;

/**
 * mybatis排序表达式
 *
 * @author 梁湛桐
 */
public final class MyBatisOrder {
    /**
     * 字段名称
     */
    private String field;
    /**
     * 是否升序
     */
    private Boolean asc = true;
    
    MyBatisOrder(String field, Boolean asc) {
        super();
        this.field = field;
        this.asc = asc;
    }
    
    String getField() {
        return this.field;
    }
    
    Boolean getAsc() {
        return this.asc;
    }
    
}
