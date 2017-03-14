package com.github.miaoxinguo.mybatis.plugin.interceptor;

import com.github.miaoxinguo.mybatis.plugin.DataSourceHolder;
import com.github.miaoxinguo.mybatis.plugin.dialect.Dialect;
import com.github.miaoxinguo.mybatis.plugin.dialect.MySql5Dialect;
import com.github.miaoxinguo.mybatis.plugin.qo.PageableQo;
import com.github.miaoxinguo.mybatis.plugin.DataSourceType;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 拦截 StatementHandler 的 prepare 方法， 组装 sql
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PrepareInterceptor extends AbstractInterceptor {

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
        if (!super.isPagedSql(parameterObject, returnType)) {
            return invocation.proceed();
        }

        String sql = this.buildPageSql(handler, metaObject);
        if ("".endsWith(sql)) {
            return invocation.proceed();
        }

        metaObject.setValue("delegate.boundSql.sql", sql);
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        return invocation.proceed();
    }

    /**
     * 根据 sql 类型 设置使用哪个数据源
     */
    private void setDataSource(SqlCommandType sqlCommandType) {
        DataSourceHolder.set(
            sqlCommandType.equals(SqlCommandType.SELECT) ? DataSourceType.SLAVE : DataSourceType.MASTER);
    }

    /**
     * 组装分页 sql
     */
    private String buildPageSql(StatementHandler handler, MetaObject metaObject) throws Throwable {
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();

        if (sql == null || sql.trim().isEmpty() || sql.contains(" limit ")) {
            return "";
        }

        // 获取 Configuration对象
        // delegate 是定义在 RoutingStatementHandler 中的属性，实际的对象是真正执行方法的 StatementHandler
        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
        String config = configuration.getVariables().getProperty("io/github/miaoxinguo/dialect");

        Dialect dialect;
        Dialect.Type dialectType = Dialect.Type.valueOf(config.toUpperCase());
        switch (dialectType) {
            case MYSQL:
                dialect = new MySql5Dialect();
                break;
            default:
                throw new SQLException("'dialect' property is invalid.");
        }

        PageableQo qo = (PageableQo) handler.getParameterHandler().getParameterObject();

        return dialect.getPagedSql(sql, qo.getOffset(), qo.getLimit());
    }

}
