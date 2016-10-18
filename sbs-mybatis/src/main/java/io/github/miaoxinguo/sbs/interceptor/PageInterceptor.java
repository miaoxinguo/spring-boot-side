package io.github.miaoxinguo.sbs.interceptor;

import io.github.miaoxinguo.sbs.qo.PageableQueryObject;
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

    /**
     * 根据参数对象判断是否是分页sql
     */
    protected boolean isPageSql(Object parameterObject) {
        return parameterObject instanceof PageableQueryObject;
    }

}
