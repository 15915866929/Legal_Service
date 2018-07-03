package com.core.base.support.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 分页拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class PaginationInterceptor implements Interceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        BoundSql boundSql = statementHandler.getBoundSql(); // 查询语句对象
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds"); // 取出分页对象
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) { // 不需要分页,直接跳到下一个拦截器
            return invocation.proceed();
        }
        //如果分页对象是自定义PageBound，则在分页前计算数据总行数
        if (rowBounds instanceof PageBound) {
            PageBound page = (PageBound) rowBounds;
            if (page.getCheckTotal()) { // 检查是否需要计算总行数
                Connection connection = (Connection) invocation.getArgs()[0];
                MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                this.setTotalCount(boundSql, connection, mappedStatement, page);
            }
        }
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        metaStatementHandler.setValue("delegate.boundSql.sql", this.getLimitSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        return invocation.proceed();
    }
    
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties arg0) {
    
    }
    
    /**
     * 从数据库里查询总的记录数，回写进分页参数<code>PageBound</code>,这样调用
     * 者就可用通过 分页参数<code>PageBound</code>获得相关信息。
     *
     * @param boundSql
     * @param connection
     * @param mappedStatement
     * @param pageBound
     */
    private void setTotalCount(BoundSql boundSql, Connection connection, MappedStatement mappedStatement, PageBound pageBound) {
        String countSql = this.getCountSql(boundSql.getSql());
        PreparedStatement countPreparedStatement = null;
        ResultSet countResultSet = null;
        try {
            countPreparedStatement = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
            if (metaParamsField != null) {
                MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
            }
            setParameters(countPreparedStatement, mappedStatement, countBS, boundSql.getParameterObject());
            countResultSet = countPreparedStatement.executeQuery();
            long totalCount = 0;
            if (countResultSet.next()) {
                totalCount = countResultSet.getLong(1);
            }
            pageBound.setTotalcount(totalCount);
            long pagecount = 0;
            if (pageBound.getPageSize() > 0) {
                pagecount = pageBound.getTotalcount() / pageBound.getPageSize() + ((pageBound.getTotalcount() % pageBound.getPageSize() == 0) ? 0 : 1);
            }
            pageBound.setPagecount(pagecount);
        } catch (Exception e) {
            log.error("获取总记录数异常", e);
        } finally {
            try {
                if (countResultSet != null && !countResultSet.isClosed()) {
                    countResultSet.close();
                }
            } catch (SQLException e) {
                log.error("获取总记录数异常", e);
            }
            try {
                if (countPreparedStatement != null && !countPreparedStatement.isClosed()) {
                    countPreparedStatement.close();
                }
            } catch (SQLException e) {
                log.error("获取总记录数异常", e);
            }
        }
    }
    
    /**
     * 查询当前页的sql
     *
     * @param sql
     * @param begin
     * @param count
     * @return
     */
    private String getLimitSql(String sql, int begin, int count) {
        return "select * from (" + sql + ") l limit " + begin + "," + count;
    }
    
    /**
     * 查询记录总数的SQL
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") c";
    }
    
    /**
     * 对SQL参数设值
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
}  