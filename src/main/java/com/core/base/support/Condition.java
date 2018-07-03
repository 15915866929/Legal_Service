package com.core.base.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 *
 * @author 梁湛桐 2012-11-29
 */
public final class Condition {
    /**
     * 默认的页码值
     */
    public final static Integer PAGE_NO = 1;
    /**
     * 默认的页记录数
     */
    public final static Integer PAGE_SIZE = 25;
    /**
     * 条件表达式
     */
    private List<Expression> expressions = new ArrayList<>();
    /**
     * 排序表达式
     */
    private List<Order> orders = new ArrayList<>();
    /**
     * 当前页码
     */
    private int pageNo = Condition.PAGE_NO;
    /**
     * 分页大小
     */
    private int pageSize = Condition.PAGE_SIZE;
    
    /**
     * 创建一个查询条件
     *
     * @return
     */
    public static Condition create() {
        return new Condition();
    }
    
    /**
     * 增加条件表达式
     *
     * @param expression 查询表达式
     * @return 条件对象
     */
    public Condition addExpression(Expression expression) {
        if (expression == null) {
            return this;
        }
        this.expressions.add(expression);
        return this;
    }
    
    /**
     * 增加排序
     *
     * @param order 排序表达式
     * @return 条件对象
     */
    public Condition addOrder(Order order) {
        if (order == null) {
            return this;
        }
        this.orders.add(order);
        return this;
    }

    /**
     * 增加排序
     * @param value 排序列名
     * @param o 1 升序 -1 降序
     * @return 条件对象
     */
    public Condition addOrder(String value,int o) {
        if(value==null || "".equals(value)){
            return this;
        }
        if(o==1){
            addOrder(Order.asc(value));
        }else if(o==-1){
            addOrder(Order.desc(value));
        }
        return this;
    }
    
    /**
     * 返回要提取的页的序号，该序号是从1开始计算的。
     */
    public int getPageNo() {
        return this.pageNo;
    }
    
    /**
     * 返回要提取的页的序号，该序号是从1开始计算的。
     *
     * @param pageNo 页的序号
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    /**
     * 返回每一页的大小，即每页的记录数。
     */
    public int getPageSize() {
        return this.pageSize;
    }
    
    /**
     * 设置每一页的大小，即每页的记录数。
     *
     * @param pageSize 每页的记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 条件表达式
     *
     * @return
     */
    List<Expression> getExpressions() {
        return this.expressions;
    }
    
    /**
     * 排序表达式
     *
     * @return
     */
    List<Order> getOrders() {
        return this.orders;
    }
    
    @Override
    public String toString() {
        return "Condition [expressions=" + this.expressions + ", orders=" + this.orders + ", pageNo=" + this.pageNo + ", pageSize=" + this.pageSize + "]";
    }
    
}
