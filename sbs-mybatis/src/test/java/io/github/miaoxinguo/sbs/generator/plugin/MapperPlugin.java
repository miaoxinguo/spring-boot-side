/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.miaoxinguo.sbs.generator.plugin;

import io.github.miaoxinguo.sbs.qo.PageQueryObject;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

/**
 * MBG 生成器插件
 *
 * http://www.mybatis.org/generator/reference/pluggingIn.html
 */
public class MapperPlugin extends PluginAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperPlugin.class);

    private final String UPDATE_NOT_EMPTY_BY_PRIMARY_KEY = "updateNotEmptyByPrimaryKey";

    /**
     *  If this method returns false, then no further methods in the plugin will be called
     */
    @Override
    public boolean validate(List<String> warnings) {
        return false;
    }

    /**
     * 为每一个 XxxDao.java 生成自定义方法
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        LOGGER.info("generate method [{}] in {}", UPDATE_NOT_EMPTY_BY_PRIMARY_KEY, interfaze.getType().toString());

        // import
        interfaze.addImportedType(new FullyQualifiedJavaType(PageQueryObject.class.getName()));

        // method name & parameter
        Method method = new Method(UPDATE_NOT_EMPTY_BY_PRIMARY_KEY);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));

        // return
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        interfaze.addMethod(method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return true;
    }

    /**
     * 为每一个 sqlMap.xml 生成自定义方法对应的标签
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        LOGGER.info("generate element <update id={}> in xml", UPDATE_NOT_EMPTY_BY_PRIMARY_KEY);

        String baseRecordType = introspectedTable.getBaseRecordType();

        XmlElement element = new XmlElement("update");
        element.addAttribute(new Attribute("id", UPDATE_NOT_EMPTY_BY_PRIMARY_KEY));
        element.addAttribute(new Attribute("parameterType", baseRecordType));

        context.getCommentGenerator().addComment(element); // 增加自动创建的注释

        String modalName = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1).toLowerCase();
        StringBuilder sb = new StringBuilder("update ").append(modalName);
        element.addElement(new TextElement(sb.toString()));

        element.addElement(new TextElement("set"));


        String template = "<if test=\"{0}!=null \">{2}=#'{'{0},jdbcType={3}'}'</if>";
        introspectedTable.getBaseColumns().forEach(introspectedColumn -> {
            Object[] params = {
                    introspectedColumn.getJavaProperty(),
                    introspectedColumn.getActualColumnName(),
                    introspectedColumn.getJdbcTypeName()};
            element.addElement(new TextElement(MessageFormat.format(template, params)));
        });

        document.getRootElement().addElement(3, element);  // index=3 表示新代码段插到原 update 后
        return true;
    }

}

