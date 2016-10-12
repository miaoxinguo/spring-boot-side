package io.github.miaoxinguo.sbs.comment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 自定义注释生成器
 */
public class CustomerCommentGenerator extends DefaultCommentGenerator {

    /**
     * xml 中的注释. 提示大家这段代码是自动生成的
     *
     * @param xmlElement the xml element
     */
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));

        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(MergeConstants.NEW_ELEMENT_TAG);
        sb.append(" - 该元素由 Mybatis-Generator 自动创建");

        String s = getDateString();
        if (s != null) {
            sb.append("于: ").append(s); //$NON-NLS-1$
        }
        sb.append("，不要修改。");
        xmlElement.addElement(new TextElement(sb.toString()));

        xmlElement.addElement(new TextElement("-->"));
    }

    // 属性的注释只包含数据库的列备注
    @Override
    public void addFieldComment(Field field, IntrospectedTable table, IntrospectedColumn column) {
        String remarks = column.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            field.addJavaDocLine("/**"); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);  //$NON-NLS-1$
            }
            field.addJavaDocLine(" */"); //$NON-NLS-1$
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        //
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable table, IntrospectedColumn column) {
        // get 方法不生成注释
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable table, IntrospectedColumn column) {
        // set 方法不生成注释
    }

}
