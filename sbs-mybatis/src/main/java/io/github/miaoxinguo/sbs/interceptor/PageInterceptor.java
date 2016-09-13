package io.github.miaoxinguo.sbs.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 自定义 Mybatis 拦截器的抽象类， 使用 mybatis 的插件机制
 *
 * 可以代理 Executor、ParameterHandler、ResultSetHandler、StatementHandler 这几个类中的方法
 * 自定义实现: 组装sql、查询、处理结果集等方法的逻辑
 */
abstract class PageInterceptor implements Interceptor {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // mybatis.conf 中 <plugin> 标签的属性 <property name="" value="" /> 中的name
    private static final String PAGE_SQL_KEY = "pageSqlKey";
    private static final String COUNT_SQL_KEY = "countSqlKey";

    // mapper.xml 文件中 分页查询<select>标签的默认id
    private static final String DEFAULT_PAGE_SQL = "selectByQueryObject";
    private static final String DEFAULT_COUNT_SQL = "selectCountByQueryObject";

    private String pageSql = DEFAULT_PAGE_SQL;
    private String countSql = DEFAULT_COUNT_SQL;

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 从 mybatis.conf 中取 <plugin> 标签下配置的属性，如没有用默认值
     */
    @Override
    public void setProperties(Properties properties) {
        if (properties == null) {
            return;
        }

        String sqlId = properties.getProperty(PAGE_SQL_KEY);
        if (sqlId != null && !sqlId.trim().isEmpty()) {
            LOGGER.info("use custom select id: {}", sqlId);
            pageSql = sqlId;
        }

        sqlId = properties.getProperty(COUNT_SQL_KEY);
        if (sqlId != null && !sqlId.trim().isEmpty()) {
            LOGGER.info("use custom select count id: {}", sqlId);
            countSql = sqlId;
        }
    }

    protected boolean isPageSql(String statement) {
        return statement != null && statement.endsWith(pageSql);
    }

    protected String buildCountStatement(String statement) {
        return statement.substring(0, statement.lastIndexOf(pageSql)) + countSql;
    }
}
