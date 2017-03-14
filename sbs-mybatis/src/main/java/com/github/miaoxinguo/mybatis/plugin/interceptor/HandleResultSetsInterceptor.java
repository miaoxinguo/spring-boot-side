package com.github.miaoxinguo.mybatis.plugin.interceptor;

import com.github.miaoxinguo.mybatis.plugin.Page;
import com.github.miaoxinguo.mybatis.plugin.qo.PageableQo;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.sql.Statement;
import java.util.List;

/**
 * 拦截 ResultSetHandler 的 handleResultSets 方法， 封装结果集
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class HandleResultSetsInterceptor extends AbstractInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        // 目标对象转换
        ResultSetHandler handler = (ResultSetHandler) invocation.getTarget();

        // MetaObject 是 Mybatis提供的一个的工具类，通过它包装一个对象后可以获取或设置该对象的原本不可访问的属性（比如那些私有属性）
        MetaObject metaObject = MetaObject.forObject(handler,
            new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

        // 判断是否是分页sql
        Class returnType = invocation.getMethod().getReturnType();
        Object parameterObject = metaObject.getValue("boundSql.parameterObject");
        if (!super.isPagedSql(parameterObject, returnType)) {
            return invocation.proceed();
        }
        PageableQo qo = (PageableQo) parameterObject;

        // 查询 count
        // MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        // Executor executor = (Executor) metaObject.getValue("executor");
        // String countSql = "select count(0) from (" + mappedStatement.getBoundSql(parameterObject) + ")";
        // // TODO 获取count的sql
        //
        // List<Integer> countList = executor.query(mappedStatement, parameterObject, RowBounds.DEFAULT, null);
        // int count = 0;
        // if (countList != null && !countList.isEmpty()) {
        //     count = countList.get(0) == null ? 0 : countList.get(0);
        // }

        // 封装返回结果
        Page page = new Page();
        page.setCurrent(qo.getPageNum());
        page.setSize(qo.getPageSize());
        page.setTotalRecord(1);
        page.setList((List)invocation.proceed());
        return page;
    }
}
