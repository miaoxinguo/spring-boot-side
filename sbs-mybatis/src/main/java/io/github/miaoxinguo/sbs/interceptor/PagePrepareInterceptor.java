package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.dialect.Dialect;
import io.github.miaoxinguo.sbs.dialect.MySql5Dialect;
import io.github.miaoxinguo.sbs.qo.PageableQueryObject;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
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
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePrepareInterceptor extends PageInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOGGER.trace("Interceptor StatementHandler.prepare start...");
        // 默认是 RoutingStatementHandler
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        LOGGER.debug("original SQL: {}", sql);

        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)handler.getParameterHandler().getParameterObject();
        if(paramMap == null) {
            return invocation.proceed();
        }

//        PageableQueryObject pageQueryObject = null;
//        for(Map.Entry entry : paramMap.entrySet()){
//            if(entry.getValue() instanceof PageableQueryObject){
//                pageQueryObject = (PageableQueryObject) entry.getValue();
//                break;
//            }
//        }


        // MetaObject是Mybatis提供的一个的工具类，通过它包装一个对象后可以获取或设置该对象的原本不可访问的属性（比如那些私有属性）
        MetaObject metaObject = MetaObject.forObject(handler,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());


        // delegate 是定义在 RoutingStatementHandler 中的属性，实际的对象是真正执行方法的 StatementHandler


        Executor executor = (Executor) metaObject.getValue("delegate.executor");

        PageableQueryObject pageQuery = (PageableQueryObject) boundSql.getParameterObject();
//        String countStatement = buildCountStatement(statement);
//        List<Integer> counts = executor.query(configuration.getMappedStatement(countStatement), pageQuery, RowBounds.DEFAULT, null);




        if (sql == null || sql.trim().isEmpty() || sql.contains(" limit ")) {
            return invocation.proceed();
        }

        // 获取 Configuration对象
        // delegate 是定义在 RoutingStatementHandler 中的属性，实际的对象是真正执行方法的 StatementHandler
        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
        String config = configuration.getVariables().getProperty("dialect");
        Dialect.Type dialectType = config == null ? Dialect.Type.MYSQL : Dialect.Type.valueOf(config.toUpperCase());

        Dialect dialect;
        switch (dialectType) {
            case MYSQL:
                dialect = new MySql5Dialect();
                break;
            default:
                throw new SQLException("'dialect' property is invalid.");
        }

        metaObject.setValue("delegate.boundSql.sql", dialect.getPageSql(sql, 100, 10));
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

//        LOGGER.debug("pagination SQL: {}", sql);
        LOGGER.trace("Interceptor StatementHandler.prepare end");
        return invocation.proceed();
    }

}
