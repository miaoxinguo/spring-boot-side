package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.dialect.Dialect;
import io.github.miaoxinguo.sbs.dialect.MySql5Dialect;
import io.github.miaoxinguo.sbs.modal.PageQueryObject;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
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
 * 代理 StatementHandler 的 prepare 方法， 组装 sql
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePrepareInterceptor extends PageInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOGGER.trace("proxy execute StatementHandler.prepare");
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)handler.getParameterHandler().getParameterObject();
        if(paramMap == null) {
            return invocation.proceed();
        }



//        PageQueryObject pageQueryObject = null;
//        for(Map.Entry entry : paramMap.entrySet()){
//            if(entry.getValue() instanceof PageQueryObject){
//                pageQueryObject = (PageQueryObject) entry.getValue();
//                break;
//            }
//        }

        // 获取MappedStatement,Configuration对象
        MetaObject metaObject = MetaObject.forObject(handler,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());


        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String statement = mappedStatement.getId();
        if (!isPageSql(statement)) {
            return invocation.proceed();
        }


        Executor executor = (Executor) metaObject.getValue("delegate.executor");

        // 获取分页参数
        BoundSql boundSql = handler.getBoundSql();
        PageQueryObject pageQuery = (PageQueryObject) boundSql.getParameterObject();
//        String countStatement = buildCountStatement(statement);
//        List<Integer> counts = executor.query(configuration.getMappedStatement(countStatement), pageQuery, RowBounds.DEFAULT, null);


        String sql = boundSql.getSql();
        LOGGER.debug("original SQL: {} ", sql);

        if (sql == null || sql.trim().isEmpty() || sql.contains(" limit ")) {
            return invocation.proceed();
        }

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

        LOGGER.debug("pagination SQL: {}", sql);
        return invocation.proceed();
    }
}
