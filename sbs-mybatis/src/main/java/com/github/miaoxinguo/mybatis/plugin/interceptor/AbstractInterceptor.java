package com.github.miaoxinguo.mybatis.plugin.interceptor;

import com.github.miaoxinguo.mybatis.plugin.Page;
import com.github.miaoxinguo.mybatis.plugin.qo.PageableQo;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * mybatis 拦截器基类.
 */
public abstract class AbstractInterceptor implements Interceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 代理哪些类
     * <p>
     * 这个方法可以替代 Mybatis 的插件拦截方式，即实现一个全局拦截器拦截所有方法，
     * 然后在 plugin 方法里做判断， 对 target 为 StatementHandler 类型的参数执行 Plugin.warp
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 从 mybatis.conf 中取 <plugin> 标签下配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 根据参数和返回类型判断是否是分页sql
     */
    boolean isPagedSql(Object parameterObject, Class returnType) {
        /*
         * 分页方法的参数必须是 PageableQo 的子类, 返回类型是 Page
         * 如果返回值是基本类型 或 Integer, 该方法应该是查询数量的方法，而不是分页方法
         */
        return parameterObject instanceof PageableQo &&  Page.class.isAssignableFrom(returnType);
    }
}
