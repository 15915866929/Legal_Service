package com.core.base.support;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * hibernate条件表达式
 *
 * @author 梁湛桐
 */
public final class MyBatisExpression {
    private String sql;
    private List<Object> params = new ArrayList<>();
    
    private MyBatisExpression(String sql) {
        super();
        this.sql = sql;
    }
    
    /**
     * and操作
     *
     * @param expressions 条件表达式
     * @return 查询表达式
     */
    static MyBatisExpression and(MyBatisExpression... expressions) {
        if ((expressions == null) || (expressions.length == 0)) {
            return null;
        }
        if (expressions.length == 1) {
            return expressions[0];
        }
        List<String> s = new ArrayList<>();
        List<Object> p = new ArrayList<>();
        for (MyBatisExpression expression : expressions) {
            s.add(expression.getSql());
            p.addAll(expression.getParams());
        }
        String sb = "(" + StringUtils.join(s, " and ") + ")";
        MyBatisExpression expression = new MyBatisExpression(sb);
        for (Object object : p) {
            expression.addParam(object);
        }
        return expression;
    }
    
    String getSql() {
        return this.sql;
    }
    
    List<Object> getParams() {
        return this.params;
    }
    
    void addParam(Object param) {
        this.params.add(param);
    }
    
    /**
     * between操作
     *
     * @param key    实体属性
     * @param value1 查询值
     * @param value2 查询值
     * @return 查询表达式
     */
    static MyBatisExpression between(String key, Object value1, Object value2) {
        if ((value1 == null) || (value2 == null)) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s BETWEEN ? AND ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value1);
        expression.addParam(value2);
        return expression;
    }
    
    /**
     * 以指定字符结束
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression endWith(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s like ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam("%" + value);
        return expression;
    }
    
    /**
     * 等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression eq(String key, Object value) {
        if (value == null) {
            return MyBatisExpression.isNull(key);
        }
        String sql = String.format("%s = ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 空值操作
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression isNull(String key) {
        String sql = String.format("%s is null", key);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 全等于查询,主要用于text字段
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression eqLike(String key, String value) {
        if (value == null) {
            return MyBatisExpression.isNull(key);
        }
        String sql = String.format("%s like ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression eqProperty(String property1, String property2) {
        String sql = String.format("%s = %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 大于等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression ge(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s >= ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，大于等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression geProperty(String property1, String property2) {
        String sql = String.format("%s >= %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 大于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression gt(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s > ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，大于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression gtProperty(String property1, String property2) {
        String sql = String.format("%s > %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * in操作
     *
     * @param key    实体属性
     * @param values 查询值
     * @return 查询表达式
     */
    static MyBatisExpression in(String key, Object... values) {
        if ((values == null) || (values.length == 0)) {
            return MyBatisExpression.sql("?=?", 0, 1);
        }
        List<Object> v = new ArrayList<>();
        v.addAll(Arrays.asList(values));
        return MyBatisExpression.in(key, v);
    }
    
    /**
     * sql条件查询
     *
     * @param sql    查询的sql
     * @param values sql里面的匿名参数值
     * @return 查询表达式
     */
    static MyBatisExpression sql(String sql, Object... values) {
        if (values == null) {
            values = new Object[]{};
        }
        MyBatisExpression expression = new MyBatisExpression(sql.trim());
        for (Object object : values) {
            expression.addParam(object);
        }
        return expression;
    }
    
    /**
     * in操作
     *
     * @param key    实体属性
     * @param values 查询值
     * @return 查询表达式
     */
    @SuppressWarnings("rawtypes")
    static MyBatisExpression in(String key, Collection values) {
        if ((values == null) || values.isEmpty()) {
            return MyBatisExpression.sql("? = ?", 0, 1);
        }
        List<String> k = new ArrayList<>();
        List<Object> v = new ArrayList<>();
        for (Object o : values) {
            if (o != null) {
                k.add("?");
                v.add(o);
            }
        }
        if (v.isEmpty()) {
            return MyBatisExpression.sql("? = ?", 0, 1);
        }
        String ks = StringUtils.join(k, ',');
        String sql = String.format("%s in (%s)", key, ks);
        MyBatisExpression expression = new MyBatisExpression(sql);
        for (Object object : v) {
            expression.addParam(object);
        }
        return expression;
    }
    
    /**
     * 非空值操作
     *
     * @param key 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression isNotNull(String key) {
        String sql = String.format("%s is not null", key);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 少于等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression le(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s <= ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，少于等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression leProperty(String property1, String property2) {
        String sql = String.format("%s <= %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 模糊查询
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression like(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s like ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam("%" + value + "%");
        return expression;
    }
    
    /**
     * 小于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression lt(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s < ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，少于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression ltProperty(String property1, String property2) {
        String sql = String.format("%s < %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 不等于操作
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression ne(String key, Object value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s <> ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value);
        return expression;
    }
    
    /**
     * 字段比较，不等于操作
     *
     * @param property1 实体属性
     * @param property2 实体属性
     * @return 查询表达式
     */
    static MyBatisExpression neProperty(String property1, String property2) {
        String sql = String.format("%s <> %s", property1, property2);
        return new MyBatisExpression(sql);
    }
    
    /**
     * 逻辑非
     *
     * @param expressions 查询表达式
     * @return 查询表达式
     */
    static MyBatisExpression not(MyBatisExpression expressions) {
        if (expressions == null) {
            return null;
        }
        String sql = expressions.getSql();
        List<Object> params = expressions.getParams();
        String sb = "not " + sql;
        MyBatisExpression expression = new MyBatisExpression(sb);
        for (Object object : params) {
            expression.addParam(object);
        }
        return expression;
    }
    
    /**
     * or操作
     *
     * @param expressions 条件表达式
     * @return 查询表达式
     */
    static MyBatisExpression or(MyBatisExpression... expressions) {
        if ((expressions == null) || (expressions.length == 0)) {
            return null;
        }
        if (expressions.length == 1) {
            return expressions[0];
        }
        List<String> s = new ArrayList<>();
        List<Object> p = new ArrayList<>();
        for (MyBatisExpression expression : expressions) {
            s.add(expression.getSql());
            p.addAll(expression.getParams());
        }
        String sb = "(" + StringUtils.join(s, " or ") + ")";
        MyBatisExpression expression = new MyBatisExpression(sb);
        for (Object object : p) {
            expression.addParam(object);
        }
        return expression;
    }
    
    /**
     * 以指定字符开头
     *
     * @param key   实体属性
     * @param value 查询值
     * @return 查询表达式
     */
    static MyBatisExpression startWith(String key, String value) {
        if (value == null) {
            throw new RuntimeException("值对象不能为空");
        }
        String sql = String.format("%s like ?", key);
        MyBatisExpression expression = new MyBatisExpression(sql);
        expression.addParam(value + "%");
        return expression;
    }
    
    /**
     * exists子查询
     *
     * @param sql    子查询语句
     * @param values sql里面的匿名参数值
     * @return
     */
    static MyBatisExpression exists(String sql, Object... values) {
        if (values == null) {
            values = new Object[]{};
        }
        String exists = String.format("exists (%s)", sql.trim());
        return MyBatisExpression.sql(exists, values);
    }
    
}
