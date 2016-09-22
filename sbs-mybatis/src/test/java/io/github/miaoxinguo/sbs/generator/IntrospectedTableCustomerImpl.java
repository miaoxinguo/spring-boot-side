package io.github.miaoxinguo.sbs.generator;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 重写 IntrospectedTable 的实现类，完全控制代码生成逻辑
 */
public class IntrospectedTableCustomerImpl extends IntrospectedTableMyBatis3SimpleImpl {

    /**
     * 替换默认实现，修改映射器文件名
     */
    @Override
    protected String calculateMyBatis3XmlMapperFileName() {
        StringBuilder sb = new StringBuilder();
        if(StringUtility.stringHasValue(this.tableConfiguration.getMapperName())) {
            String mapperName = this.tableConfiguration.getMapperName();
            int ind = mapperName.lastIndexOf(46);
            if(ind == -1) {
                sb.append(mapperName);
            } else {
                sb.append(mapperName.substring(ind + 1));
            }

            sb.append(".xml");
        } else {
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append(".xml");
        }
        return super.calculateMyBatis3XmlMapperFileName();
    }

    /**
     * 替换默认实现，修改 repository 层文件名
     */
    protected void calculateJavaClientAttributes() {
        if(this.context.getJavaClientGeneratorConfiguration() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaClientImplementationPackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAOImpl");
            this.setDAOImplementationType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("DAO");
            this.setDAOInterfaceType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if(StringUtility.stringHasValue(this.tableConfiguration.getMapperName())) {
                sb.append(this.tableConfiguration.getMapperName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("Repository");
            }

            this.setMyBatis3JavaMapperType(sb.toString());
            sb.setLength(0);
            sb.append(this.calculateJavaClientInterfacePackage());
            sb.append('.');
            if(StringUtility.stringHasValue(this.tableConfiguration.getSqlProviderName())) {
                sb.append(this.tableConfiguration.getSqlProviderName());
            } else {
                sb.append(this.fullyQualifiedTable.getDomainObjectName());
                sb.append("SqlProvider");
            }

            this.setMyBatis3SqlProviderType(sb.toString());
        }
    }

}
