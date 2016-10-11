package io.github.miaoxinguo.sbs;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;

/**
 * 重写 IntrospectedTable 的实现类，完全控制代码生成逻辑
 */
public class IntrospectedTableCustomerImpl extends IntrospectedTableMyBatis3SimpleImpl {

    /**
     * 替换默认实现，修改映射器文件名
     */
    @Override
    protected String calculateMyBatis3XmlMapperFileName() {
        String xmlMapperFileName= super.calculateMyBatis3XmlMapperFileName();
        return xmlMapperFileName.replace("Mapper.xml", ".xml");
    }

}
