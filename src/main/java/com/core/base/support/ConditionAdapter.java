package com.core.base.support;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件查询适配器
 *
 * @author lzt 2013-8-14
 */
public final class ConditionAdapter {
    
    private ConditionAdapter() {
    }
    
    /**
     * 获取MyBatis的查询对象
     *
     * @param condition 条件对象
     * @param needOrder 是否附带排序条件
     * @param clazz     查询对象类型
     * @return
     */
    public static <T> Wrapper<T> getWrapper(Condition condition, boolean needOrder, Class<T> clazz) {
        // 如果没有条件,立即增加一个永真条件
        if (condition.getExpressions().isEmpty()) {
            condition.addExpression(Expression.sql("?=?", 1, 1));
        }
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        // 处理条件
        List<String> esql = new ArrayList<>();
        List<Object> p = new ArrayList<>();
        int i = 0;
        for (Expression expression : condition.getExpressions()) {
            MyBatisExpression myBatisExpression = ConditionAdapter.toMyBatisExpression(expression);
            String sql = myBatisExpression.getSql();
            while (StringUtils.contains(sql, "?")) {
                sql = StringUtils.replaceOnce(sql, "?", "{" + i + "}");
                i++;
            }
            esql.add(sql);
            p.addAll(myBatisExpression.getParams());
        }
        String sql = StringUtils.join(esql, " and ");
        wrapper.addFilter(sql, p.toArray());
        
        // 处理排序
        if (needOrder) {
            for (Order order : condition.getOrders()) {
                if (order == null) {
                    continue;
                }
                MyBatisOrder myBatisOrder = ConditionAdapter.toMyBatisOrder(order);
                wrapper.orderBy(myBatisOrder.getField(), myBatisOrder.getAsc());
            }
        }
        wrapper.isWhere(true);
        return wrapper;
    }
    
    /**
     * 获取MyBatis的查询对象,默认带排序条件
     *
     * @param condition 条件对象
     * @param clazz     查询对象类型
     * @return
     */
    public static <T> Wrapper<T> getWrapper(Condition condition, Class<T> clazz) {
        return ConditionAdapter.getWrapper(condition, true, clazz);
    }
    
    /**
     * 转换成mybatis的条件
     *
     * @param expression
     * @return
     */
    private static MyBatisExpression toMyBatisExpression(Expression expression) {
        MyBatisExpression ret;
        switch (expression.getOperation()) {
            case "between":
                ret = MyBatisExpression.between(expression.getKey1(), expression.getValue1(), expression.getValue2());
                break;
            case "endWith":
                ret = MyBatisExpression.endWith(expression.getKey1(), expression.getValue1().toString());
                break;
            case "eq":
                ret = MyBatisExpression.eq(expression.getKey1(), expression.getValue1());
                break;
            case "eqLike":
                ret = MyBatisExpression.eqLike(expression.getKey1(), expression.getValue1().toString());
                break;
            case "eqProperty":
                ret = MyBatisExpression.eqProperty(expression.getKey1(), expression.getKey2());
                break;
            case "ge":
                ret = MyBatisExpression.ge(expression.getKey1(), expression.getValue1());
                break;
            case "geProperty":
                ret = MyBatisExpression.geProperty(expression.getKey1(), expression.getKey2());
                break;
            case "gt":
                ret = MyBatisExpression.gt(expression.getKey1(), expression.getValue1());
                break;
            case "gtProperty":
                ret = MyBatisExpression.gtProperty(expression.getKey1(), expression.getKey2());
                break;
            case "in":
                ret = MyBatisExpression.in(expression.getKey1(), expression.getValue3());
                break;
            case "isNotNull":
                ret = MyBatisExpression.isNotNull(expression.getKey1());
                break;
            case "isNull":
                ret = MyBatisExpression.isNull(expression.getKey1());
                break;
            case "le":
                ret = MyBatisExpression.le(expression.getKey1(), expression.getValue1());
                break;
            case "leProperty":
                ret = MyBatisExpression.leProperty(expression.getKey1(), expression.getKey2());
                break;
            case "like":
                ret = MyBatisExpression.like(expression.getKey1(), expression.getValue1().toString());
                break;
            case "lt":
                ret = MyBatisExpression.lt(expression.getKey1(), expression.getValue1());
                break;
            case "ltProperty":
                ret = MyBatisExpression.ltProperty(expression.getKey1(), expression.getKey2());
                break;
            case "ne":
                ret = MyBatisExpression.ne(expression.getKey1(), expression.getValue1());
                break;
            case "neProperty":
                ret = MyBatisExpression.neProperty(expression.getKey1(), expression.getKey2());
                break;
            case "sql":
                ret = MyBatisExpression.sql(expression.getKey1(), expression.getValue4());
                break;
            case "startWith":
                ret = MyBatisExpression.startWith(expression.getKey1(), expression.getValue1().toString());
                break;
            case "not":
                MyBatisExpression me = ConditionAdapter.toMyBatisExpression(expression.getChildren().get(0));
                ret = MyBatisExpression.not(me);
                break;
            case "and":
                List<Expression> ac = expression.getChildren();
                List<MyBatisExpression> ahes = new ArrayList<>();
                for (Expression ae : ac) {
                    MyBatisExpression at = ConditionAdapter.toMyBatisExpression(ae);
                    if (at != null) {
                        ahes.add(at);
                    }
                }
                ret = MyBatisExpression.and(ahes.toArray(new MyBatisExpression[ahes.size()]));
                break;
            case "or":
                List<Expression> oc = expression.getChildren();
                List<MyBatisExpression> ohes = new ArrayList<>();
                for (Expression oe : oc) {
                    MyBatisExpression ot = ConditionAdapter.toMyBatisExpression(oe);
                    if (ot != null) {
                        ohes.add(ot);
                    }
                }
                ret = MyBatisExpression.or(ohes.toArray(new MyBatisExpression[ohes.size()]));
                break;
            case "exists":
                ret = MyBatisExpression.exists(expression.getKey1(), expression.getValue4());
                break;
            default:
                throw new RuntimeException("不支持的类型：" + expression.getOperation());
        }
        return ret;
    }
    
    /**
     * 转换成mybatis的排序
     *
     * @param order
     * @return
     */
    private static MyBatisOrder toMyBatisOrder(Order order) {
        if (order.getAsc()) {
            return new MyBatisOrder(order.getKey(), true);
        } else {
            return new MyBatisOrder(order.getKey(), false);
        }
    }
}
