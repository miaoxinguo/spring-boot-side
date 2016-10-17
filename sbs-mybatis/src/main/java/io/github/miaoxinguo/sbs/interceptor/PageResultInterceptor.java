package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.PageResult;
import io.github.miaoxinguo.sbs.qo.PageableQueryObject;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截 StatementHandler 的 handleResultSets 方法， 封装结果集
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageResultInterceptor extends PageInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 目标对象转换
        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();

        MetaObject metaObject = MetaObject.forObject(resultSetHandler,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

        // Configuration、 BoundSql 对象
        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");


        BoundSql boundSql = (BoundSql) metaObject.getValue("boundSql");

        // 是否是分页 sql
        if(!(boundSql.getParameterObject() instanceof PageableQueryObject)) {
            return invocation.proceed();
        }

        Executor executor = (Executor) metaObject.getValue("delegate.executor");
        PageableQueryObject pageQueryObject = (PageableQueryObject) boundSql.getParameterObject();
        String countStatement = buildCountStatement();
        List<Integer> count = executor.query(configuration.getMappedStatement(countStatement), pageQueryObject, RowBounds.DEFAULT, null);

        List<PageResult> result = new ArrayList<>(1);
        PageResult pageResult = new PageResult();
        pageResult.setCurrentPage(pageQueryObject.getPageNum());
        pageResult.setPageSize(pageQueryObject.getPageSize());
        pageResult.setList((List) invocation.proceed());
        pageResult.setTotalPage(count.get(0));

        result.add(pageResult);
        return result;
    }

    /**
     *
     */
    private String buildCountStatement() {

    }
}
