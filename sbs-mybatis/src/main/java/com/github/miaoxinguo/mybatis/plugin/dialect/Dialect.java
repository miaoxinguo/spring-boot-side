package com.github.miaoxinguo.mybatis.plugin.dialect;

/**
 * 支持的数据库语言
 */
public interface Dialect {

    enum Type {
        MYSQL
    }

    /**
     * 获取分页sql
     */
    String getPagedSql(String sql, int offset, int limit);

}