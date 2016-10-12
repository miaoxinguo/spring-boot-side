package io.github.miaoxinguo.sbs.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 自动生成代码的插件
 */
public class MapperPlugin extends PluginAdapter {

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
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        replaceIdValue(element, "selectById");
        return true;
    }

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

    /*
     * 替换 id 的值为接口中的方法名
     */
    private void replaceIdValue(XmlElement element, String newValue) {
        Iterator<Attribute> it = element.getAttributes().iterator();
        while(it.hasNext()) {
            if(it.next().getName().equals("id")) {
                it.remove();
                break;
            }
        }
        element.getAttributes().add(new Attribute("id", newValue));
    }

    // 不生成 findAll 方法
    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 当 doc 生成后执行
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        addBaseColumnList(document.getRootElement(), introspectedTable);
//        addUpdateSelectiveById(document.getRootElement(), introspectedTable);

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

        // 增加分割线
        Element lastElement = elements.get(elements.size() -1);
        System.out.println(lastElement.getFormattedContent(0));
        if(!lastElement.getFormattedContent(0).contains("分割线")) {
            elements.add(new TextElement("<!-- 分割线 新增的代码写在此处以下 -->"));
        }
        return true;
    }

    /**
     * 增加 sql 片段, base columns, 即所有列
     *
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BaseColumnListElementGenerator
     */
    private void addBaseColumnList(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getBaseColumnListId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable.getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 100) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        parentElement.addElement(answer);
    }

    /**
     * 增加 sql 片段, base columns, 即所有列
     *
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.UpdateByPrimaryKeySelectiveElementGenerator
     */
    private void addUpdateSelectiveById(XmlElement rootElement, IntrospectedTable introspectedTable) {

        XmlElement answer = new XmlElement("update");

        answer.addAttribute(new Attribute("id", introspectedTable.getUpdateByExampleSelectiveStatementId())); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getAllColumns())) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty("record.")); //$NON-NLS-1$
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                    introspectedColumn, "record.")); //$NON-NLS-1$
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
                        introspectedTable)) {
            rootElement.addElement(answer);
        }

        rootElement.addElement(answer);
    }


}
