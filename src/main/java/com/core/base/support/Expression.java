package com.core.base.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 条件表达式
 *
 * @author 梁湛桐
 */
public final class Expression {
    private String key1;
    private String key2;
    private String operation;
    private Object value1;
    private Object value2;
    @SuppressWarnings("rawtypes")
    private Collection value3;
    private Object[] value4;
    private List<Expression> children = new ArrayList<>();
    
    private Expression() {
        super();
    }
    
    /**
     * and操作
     *
     * @param expressions 条件表达式
     * @return 查询表达式
     */
    public static Expression and(Expression... expressions) {
        if ((expressions == null) || (expressions.length == 0)) {
            return null;
        }
        Expression ret = new Expression();
        ret.operation = "and";
        ret.children.addAll(Arrays.asList(expressions));
        return ret;
    }
    
    /**
     * between操作
     *
     * @param key    实体属性
     * @param value1 查询值
     * @param value2 查询值
     * @return 查询表达式
     */
    public static Expression between(String key, Object value1, Object value2) {
        if ((value1 == null) || (value2 == null)) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value1;
        expression.value2 = value2;
        expression.operation = "between";
        return expression;
    }
    
    /**
     * 以指定字符结束
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression endWith(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "endWith";
        return expression;
    }
    
    /**
     * 等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression eq(String key, Object value) {
        if (value == null) {
            return Expression.isNull(key);
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "eq";
        return expression;
    }
    
    /**
     * 空值操作
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    public static Expression isNull(String key) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.operation = "isNull";
        return expression;
    }
    
    /**
     * 全等于查询,主要用于text字段
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression eqLike(String key, String value) {
        if (value == null) {
            return Expression.isNull(key);
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "eqLike";
        return expression;
    }
    
    /**
     * 字段比较，等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression eqProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "eqProperty";
        return expression;
    }
    
    /**
     * 大于等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression ge(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "ge";
        return expression;
    }
    
    /**
     * 字段比较，大于等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression geProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "geProperty";
        return expression;
    }
    
    /**
     * 大于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression gt(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "gt";
        return expression;
    }
    
    /**
     * 字段比较，大于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression gtProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "gtProperty";
        return expression;
    }
    
    /**
     * in操作
     *
     * @param key    实体属性
     * @param values 查询值
     * @return 查询表达式
     */
    public static Expression in(String key, Object... values) {
        List<Object> list = new ArrayList<>();
        list.addAll(Arrays.asList(values));
        return Expression.in(key, list);
    }
    
    /**
     * in操作
     *
     * @param key    实体属性
     * @param values 查询值
     * @return 查询表达式
     */
    @SuppressWarnings("rawtypes")
    public static Expression in(String key, Collection values) {
        if ((values == null) || values.isEmpty()) {
            return Expression.sql("?=?", 0, 1);
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value3 = values;
        expression.operation = "in";
        return expression;
    }
    
    /**
     * sql条件查询
     *
     * @param sql    查询的sql
     * @param values sql里面的匿名参数值
     * @return 查询表达式
     */
    public static Expression sql(String sql, Object... values) {
        Expression expression = new Expression();
        expression.key1 = sql;
        expression.value4 = values;
        expression.operation = "sql";
        return expression;
    }
    
    /**
     * collection属性是空的
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    public static Expression isEmpty(String key) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.operation = "isEmpty";
        return expression;
    }
    
    /**
     * collection属性不是空的
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    public static Expression isNotEmpty(String key) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.operation = "isNotEmpty";
        return expression;
    }
    
    /**
     * 非空值操作
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    public static Expression isNotNull(String key) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.operation = "isNotNull";
        return expression;
    }
    
    /**
     * 少于等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression le(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "le";
        return expression;
    }
    
    /**
     * 字段比较，少于等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression leProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "leProperty";
        return expression;
    }
    
    /**
     * 模糊查询
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression like(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "like";
        return expression;
    }
    
    /**
     * 小于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression lt(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "lt";
        return expression;
    }
    
    /**
     * 字段比较，少于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression ltProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "ltProperty";
        return expression;
    }
    
    /**
     * 不等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression ne(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "ne";
        return expression;
    }
    
    /**
     * 字段比较，不等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    public static Expression neProperty(String property1, String property2) {
        Expression expression = new Expression();
        expression.key1 = property1;
        expression.key2 = property2;
        expression.operation = "neProperty";
        return expression;
    }
    
    /**
     * 逻辑非
     *
     * @param expression 查询表达式
     * @return 查询表达式
     */
    public static Expression not(Expression expression) {
        if (expression == null) {
            return null;
        }
        Expression ret = new Expression();
        ret.operation = "not";
        ret.children.add(expression);
        return ret;
    }
    
    /**
     * or操作
     *
     * @param expressions 条件表达式
     * @return 查询表达式
     */
    public static Expression or(Expression... expressions) {
        if ((expressions == null) || (expressions.length == 0)) {
            return null;
        }
        Expression ret = new Expression();
        ret.operation = "or";
        ret.children.addAll(Arrays.asList(expressions));
        return ret;
    }
    
    /**
     * collection属性的size为指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeEq(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeEq";
        return expression;
    }
    
    /**
     * collection属性的size大于等于指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeGe(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeGe";
        return expression;
    }
    
    /**
     * collection属性的size大于指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeGt(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeGt";
        return expression;
    }
    
    /**
     * collection属性的size小于等于指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeLe(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeLe";
        return expression;
    }
    
    /**
     * collection属性的size小于指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeLt(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeLt";
        return expression;
    }
    
    /**
     * collection属性的size不为指定值
     *
     * @param key  实体属性
     * @param size 查询值
     * @return 查询表达式
     */
    public static Expression sizeNe(String key, int size) {
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = size;
        expression.operation = "sizeNe";
        return expression;
    }
    
    /**
     * exists子查询
     *
     * @param sql    子查询语句
     * @param values sql里面的匿名参数值
     * @return 查询表达式
     */
    public static Expression exists(String sql, Object... values) {
        Expression expression = new Expression();
        expression.key1 = sql;
        expression.value4 = values;
        expression.operation = "exists";
        return expression;
    }
    
    /**
     * 以指定字符开头
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    public static Expression startWith(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        Expression expression = new Expression();
        expression.key1 = key;
        expression.value1 = value;
        expression.operation = "startWith";
        return expression;
    }
    
    List<Expression> getChildren() {
        return this.children;
    }
    
    String getKey1() {
        return this.key1;
    }
    
    String getKey2() {
        return this.key2;
    }
    
    String getOperation() {
        return this.operation;
    }
    
    Object getValue1() {
        return this.value1;
    }
    
    Object getValue2() {
        return this.value2;
    }
    
    @SuppressWarnings("rawtypes")
    Collection getValue3() {
        return this.value3;
    }
    
    Object[] getValue4() {
        return this.value4;
    }
    
    @Override
    public String toString() {
        return "Expression [key1=" + this.key1 + ", key2=" + this.key2 + ", operation=" + this.operation + ", value1=" + this.value1 + ", value2=" + this.value2 + ", value3=" + this.value3 + ", value4=" + Arrays.toString(this.value4) + ", children=" + this.children + "]";
    }
    
}
