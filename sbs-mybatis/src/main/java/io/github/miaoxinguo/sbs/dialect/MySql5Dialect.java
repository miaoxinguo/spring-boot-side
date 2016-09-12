package io.github.miaoxinguo.sbs.dialect;

/**
 * Mysql 方言
 */
public class MySql5Dialect implements Dialect {

    public String getPageSql(String sql, int offset, int limit) {
        StringBuilder builder = new StringBuilder(sql);  // default length is sql.length + 16
        builder.append(" limit ");
        if (offset > 0) {
            builder.append(offset).append(",").append(limit);
        } else {
            builder.append(limit);
        }
        return builder.toString();
    }

}
