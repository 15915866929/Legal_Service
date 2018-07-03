package com.core.base.support;

/**
 * 排序表达式
 *
 * @author 梁湛桐
 */
public final class Order {
    
    private String key;
    private Boolean asc = true;
    
    private Order(String key, Boolean asc) {
        super();
        this.key = key;
        this.asc = asc;
    }
    
    /**
     * 升序
     *
     * @param key 实体属性
     * @return 排序表达式
     */
    public static Order asc(String key) {
        return new Order(key, true);
    }
    
    /**
     * 降序
     *
     * @param key 实体属性
     * @return 排序表达式
     */
    public static Order desc(String key) {
        return new Order(key, false);
    }
    
    String getKey() {
        return this.key;
    }
    
    Boolean getAsc() {
        return this.asc;
    }
    
    @Override
    public String toString() {
        return "Order [key=" + this.key + ", asc=" + this.asc + "]";
    }
}
