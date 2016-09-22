package io.github.miaoxinguo.sbs.resolver;

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
     * tinyint   -> int
     * smallint  -> int
     */
    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        switch (column.getJdbcType()) {
            case Types.TINYINT:
            case Types.SMALLINT:
                return FullyQualifiedJavaType.getIntInstance();
        }

        return super.overrideDefaultType(column, defaultType);
    }
}
