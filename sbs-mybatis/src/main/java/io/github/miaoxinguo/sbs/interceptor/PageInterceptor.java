package io.github.miaoxinguo.sbs.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 自定义 Mybatis 拦截器的抽象类， 使用 mybatis 的插件机制
 *
 * 可以代理 StatementHandler 的方法， 自定义实现: 组装sql、查询、处理结果集 等方法的逻辑
 */
public abstract class PageInterceptor implements Interceptor {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String PAGE_SQL_KEY = "pageSqlKey";
    private static final String COUNT_SQL_KEY = "countSqlKey";

    public static final String DEFAULT_PAGE_SQL = "findByQuery";
    public static final String DEFAULT_COUNT_SQL = "findCountByQuery";

    protected String pageSql = DEFAULT_PAGE_SQL;
    protected String countSql = DEFAULT_COUNT_SQL;

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        if (properties != null) {
            String sqlId = properties.getProperty(PAGE_SQL_KEY);
            if (sqlId != null && !sqlId.trim().isEmpty()) {
                pageSql = sqlId;
            }

            sqlId = properties.getProperty(COUNT_SQL_KEY);
            if (sqlId != null && !sqlId.trim().isEmpty()) {
                countSql = sqlId;
            }
        }
    }

    protected boolean isPageSql(String statement) {
        return statement == null ? false : statement.endsWith(pageSql);
    }

    protected String buildCountStatement(String statement) {
        return statement.substring(0, statement.lastIndexOf(pageSql)) + countSql;
    }
}
