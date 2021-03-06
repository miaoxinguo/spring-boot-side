package com.github.miaoxinguo.resolver;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * 扩展 MBG 的类型解析器
 */
public class JavaTypeResolverCustomerImpl extends JavaTypeResolverDefaultImpl {

    /**
     * 覆盖默认的映射类型
     *
     * tinyint   -> Integer
     * smallint  -> Integer
     * timestamp -> LocalDateTime
     */
    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        switch (column.getJdbcType()) {
            case Types.TINYINT:
            case Types.SMALLINT:
                return new FullyQualifiedJavaType("java.lang.Integer");
            case Types.TIMESTAMP:
                return new FullyQualifiedJavaType("java.time.LocalDateTime");
        }

        return super.overrideDefaultType(column, defaultType);
    }
}
