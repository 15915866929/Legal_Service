package com.core.configuration;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.proxy.jdbc.*;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.SQLUtils.FormatOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源配置类
 *
 * @author lzt
 * @date 2017年10月10日
 */
@Configuration
public class DruidDataSourceConfig {
    
    /**
     * 数据源
     *
     * @return
     * @throws SQLException
     */
    @Bean
    public DataSource druidDataSource() throws SQLException {
        return DruidDataSourceBuilder.create().build();
    }
    
    /**
     * 数据源的SLF4J
     *
     * @return
     */
    @Bean
    public Slf4jLogFilter slf4jLogFiter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter() {
            
            @Override
            protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
                String formatsql = this.getSql(statement, sql);
                statement.setLastExecuteTimeNano();
                double nanos = statement.getLastExecuteTimeNano();
                double millis = nanos / (1000 * 1000);
                this.statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + this.stmtId(statement) + "} executed. " + millis + " millis. " + System.getProperty("line.separator") + formatsql);
            }
            
            @Override
            protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
                String sql;
                if (statement instanceof PreparedStatementProxy) {
                    sql = ((PreparedStatementProxy) statement).getSql();
                } else {
                    sql = statement.getBatchSql();
                }
                String formatsql = this.getSql(statement, sql);
                statement.setLastExecuteTimeNano();
                double nanos = statement.getLastExecuteTimeNano();
                double millis = nanos / (1000 * 1000);
                this.statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + this.stmtId(statement) + "} batch executed. " + millis + " millis. " + System.getProperty("line.separator") + formatsql);
            }
            
            @Override
            protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
                String formatsql = this.getSql(statement, sql);
                statement.setLastExecuteTimeNano();
                double nanos = statement.getLastExecuteTimeNano();
                double millis = nanos / (1000 * 1000);
                this.statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + this.stmtId(statement) + "} query executed. " + millis + " millis. " + System.getProperty("line.separator") + formatsql);
            }
            
            @Override
            protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
                String formatsql = this.getSql(statement, sql);
                statement.setLastExecuteTimeNano();
                double nanos = statement.getLastExecuteTimeNano();
                double millis = nanos / (1000 * 1000);
                this.statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + this.stmtId(statement) + "} updateById executed. effort " + updateCount + ". " + millis + " millis. " + System.getProperty("line.separator") + formatsql);
            }
            
            @Override
            public void resultSet_close(FilterChain chain, ResultSetProxy resultSet) throws SQLException {
                super.resultSet_close(chain, resultSet);
                String buf = "{conn-" + resultSet.getStatementProxy().getConnectionProxy().getId() + ", " + stmtId(resultSet.getStatementProxy()) + ", rs-" + resultSet.getId() + "} closed" + System.getProperty("line.separator") + "共查询出" + resultSet.getFetchRowCount() + "条记录";
                this.statementLog(buf);
            }
            
            @Override
            protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
                String formatsql = this.getSql(statement, sql);
                this.statementLogError("{conn-" + statement.getConnectionProxy().getId() + ", " + this.stmtId(statement) + "} execute error. " + System.getProperty("line.separator") + formatsql, error);
            }
            
            private String getSql(StatementProxy statement, String sql) {
                int parametersSize = statement.getParametersSize();
                List<Object> parameters = new ArrayList<>(parametersSize);
                for (int i = 0; i < parametersSize; ++i) {
                    JdbcParameter jdbcParam = statement.getParameter(i);
                    parameters.add(jdbcParam != null ? jdbcParam.getValue() : null);
                }
                String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
                return SQLUtils.format(sql, dbType, parameters, this.getStatementSqlFormatOption());
            }
            
            private String stmtId(StatementProxy statement) {
                StringBuilder buf = new StringBuilder();
                if (statement instanceof CallableStatementProxy) {
                    buf.append("cstmt-");
                } else if (statement instanceof PreparedStatementProxy) {
                    buf.append("pstmt-");
                } else {
                    buf.append("stmt-");
                }
                buf.append(statement.getId());
                
                return buf.toString();
            }
            
        };
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
        slf4jLogFilter.setStatementParameterSetLogEnabled(false);
        slf4jLogFilter.setStatementExecuteAfterLogEnabled(false);
        slf4jLogFilter.setStatementCloseAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteQueryAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteUpdateAfterLogEnabled(false);
        FormatOption option = new FormatOption(true, true, false);
        option.setDesensitize(false);
        slf4jLogFilter.setStatementSqlFormatOption(option);
        return slf4jLogFilter;
    }
    
}
