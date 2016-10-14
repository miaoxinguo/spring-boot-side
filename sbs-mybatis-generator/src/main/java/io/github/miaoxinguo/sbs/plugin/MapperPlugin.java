package io.github.miaoxinguo.sbs.plugin;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成代码的插件
 */
public class MapperPlugin extends PluginAdapter {

    // sqlMap.xml 自动生成的标签顺序
    private static final List<String> elementOrder = new ArrayList<>();
    static {
        elementOrder.add("resultMap");
        elementOrder.add("sql");
        elementOrder.add("insert");
        elementOrder.add("delete");
        elementOrder.add("update");
        elementOrder.add("select");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    // ------------  按接口中的方法名修改映射器文件中的id

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceIdValue(element, "deleteById");
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceIdValue(element, "update");
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceIdValue(element, "updateSelectiveById");
        return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().removeIf(attribute -> attribute.getName().equals("resultMap"));

        element.addAttribute(new Attribute("resultType", introspectedTable.getBaseRecordType()));
        replaceIdValue(element, "selectById");
        return true;
    }

    /*
     * 替换 id 的值为接口中的方法名
     */
    private void replaceIdValue(XmlElement element, String newValue) {
        element.getAttributes().removeIf(attribute -> attribute.getName().equals("id"));
        element.getAttributes().add(new Attribute("id", newValue));
    }

    // 不生成 findAll 方法
    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 当 doc 生成后执行
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 替换 namespace
        document.getRootElement().getAttributes().removeIf(attribute -> attribute.getName().equals("namespace"));
        String entityName = introspectedTable.getBaseRecordType();
        String namespace =  context.getProperty("namespace") + entityName.substring(entityName.lastIndexOf(".")) +"Repository";
        document.getRootElement().addAttribute(new Attribute("namespace", namespace));

        // 增加方法
        addBaseColumnList(document.getRootElement(), introspectedTable);
        addUpdateSelectiveById(document.getRootElement(), introspectedTable);

        List<Element> elements = document.getRootElement().getElements();

        // 排序
        elements.sort((o1, o2) -> {
            XmlElement e1 = (XmlElement) o1;
            XmlElement e2 = (XmlElement) o2;
            return elementOrder.indexOf(e1.getName()) - elementOrder.indexOf(e2.getName());
        });

        // 每个元素后插入空行
        for (int index = 1; index < elements.size(); index+=2) {
            elements.add(index, new TextElement(""));
        }
        return true;
    }

    /**
     * 增加 sql 片段, base columns, 即所有列
     *
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator
     */
    private void addBaseColumnList(XmlElement parentElement, IntrospectedTable introspectedTable) {
        BaseColumnListElementGenerator generator = new BaseColumnListElementGenerator();
        generator.setIntrospectedTable(introspectedTable);
        generator.setContext(context);
        generator.addElements(parentElement);
    }

    /**
     * 增加 sql 片段, base columns, 即所有列
     *
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator
     */
    private void addUpdateSelectiveById(XmlElement parentElement, IntrospectedTable introspectedTable) {
        UpdateByPrimaryKeySelectiveElementGenerator generator = new UpdateByPrimaryKeySelectiveElementGenerator();
        generator.setIntrospectedTable(introspectedTable);
        generator.setContext(context);
        generator.addElements(parentElement);
    }


}
