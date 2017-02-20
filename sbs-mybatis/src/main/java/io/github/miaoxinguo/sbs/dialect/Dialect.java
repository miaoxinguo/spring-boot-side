package io.github.miaoxinguo.sbs.dialect;

/**
 * 支持的数据库语言
 */
public interface Dialect {

    enum Type {
        MYSQL
    }

    String getPagedSql(String sql, int offset, int limit);
}