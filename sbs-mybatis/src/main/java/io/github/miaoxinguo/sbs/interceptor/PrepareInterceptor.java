package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.DataSourceType;
import io.github.miaoxinguo.sbs.RoutingDataSource;
import io.github.miaoxinguo.sbs.dialect.Dialect;
import io.github.miaoxinguo.sbs.dialect.MySql5Dialect;
import io.github.miaoxinguo.sbs.qo.PageableQueryObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static io.github.miaoxinguo.sbs.DataSourceType.SLAVES;

/**
 * 拦截 StatementHandler 的 prepare 方法， 组装 sql
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PrepareInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget(); // 默认是 RoutingStatementHandler

        // MetaObject 是 Mybatis提供的一个的工具类，通过它包装一个对象后可以获取或设置该对象的原本不可访问的属性（比如那些私有属性）
        MetaObject metaObject = MetaObject.forObject(handler,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

        // 1、根据sql类型设置数据源
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        this.setDataSource(mappedStatement.getSqlCommandType());

        // 2、组装分页 sql
        Class returnType = invocation.getMethod().getReturnType();
        Object parameterObject = handler.getParameterHandler().getParameterObject();
        if(isPageSql(parameterObject, returnType)) {
            buildPageSql(handler, metaObject);
        }

        return invocation.proceed();
    }

    /**
     * 根据 sql 类型 设置使用哪个数据源
     */
    private void setDataSource(SqlCommandType sqlCommandType) {
        if(sqlCommandType.equals(SqlCommandType.SELECT)) {
            int count = SLAVES.size();
            String dataSource = DataSourceType.SLAVES.get(RandomUtils.nextInt(0, count));
            RoutingDataSource.setDataSource(dataSource);
        }
        else {
            RoutingDataSource.setDataSource(DataSourceType.MASTER);
        }
    }

    /**
     * 组装分页 sql
     */
    private void buildPageSql(StatementHandler handler, MetaObject metaObject) throws Throwable {
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();

        if (sql == null || sql.trim().isEmpty() || sql.contains(" limit ")) {
            return;
        }

        // 获取 Configuration对象
        // delegate 是定义在 RoutingStatementHandler 中的属性，实际的对象是真正执行方法的 StatementHandler
        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
        String config = configuration.getVariables().getProperty("dialect");

        Dialect dialect;
        Dialect.Type dialectType = Dialect.Type.valueOf(config.toUpperCase());
        switch (dialectType) {
            case MYSQL:
                dialect = new MySql5Dialect();
                break;
            default:
                throw new SQLException("'dialect' property is invalid.");
        }

        PageableQueryObject qo = (PageableQueryObject)handler.getParameterHandler().getParameterObject();
        metaObject.setValue("delegate.boundSql.sql", dialect.getPageSql(sql, qo.getPageNum()-1, qo.getPageSize()));
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
    }

    /**
     * 根据参数对象判断是否是分页sql
     */
    private boolean isPageSql(Object parameterObject, Class returnType) {
        // 如果返回类型是
        boolean validParameter = parameterObject instanceof PageableQueryObject;
        boolean invalidReturnType = returnType.isPrimitive() || returnType.isInstance(Integer.class);

        return validParameter && !invalidReturnType;
    }

    /**
     * 代理哪些类
     *
     * <p>这个方法可以替代 Mybatis 的插件拦截方式，即实现一个全局拦截器拦截所有方法，
     * 然后在 plugin 方法里做判断， 对 target 为 StatementHandler 类型的参数执行 Plugin.warp
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 从 mybatis.conf 中取 <plugin> 标签下配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
