package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.modal.PageQueryObject;
import io.github.miaoxinguo.sbs.modal.PageResult;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截 StatementHandler 的 handleResultSets 方法， 组装 sql
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageResultInterceptor extends PageInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 目标对象转换
        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();

        // 获取MappedStatement,Configuration对象
        MetaObject metaObject = MetaObject.forObject(resultSetHandler,
                new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());


        // 获取分页参数
        PageQueryObject pageQuery = (PageQueryObject) metaObject.getValue("boundSql.parameterObject");

        List<PageResult> result = new ArrayList<>(1);
        PageResult page = new PageResult();
//        page.setPagination(pageQuery.);
        page.setList((List) invocation.proceed());
        result.add(page);

        return result;
    }
}
