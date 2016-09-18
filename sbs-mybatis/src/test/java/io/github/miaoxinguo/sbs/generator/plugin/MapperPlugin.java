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

import io.github.miaoxinguo.sbs.modal.PageQueryObject;
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

import java.util.List;

/**
 * MBG 生成器插件
 *
 * http://www.mybatis.org/generator/reference/pluggingIn.html
 */
public class MapperPlugin extends PluginAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperPlugin.class);

    private final String SELECT_BY_QUERY_OBJECT = "selectByQueryObject";

    /**
     *  If this method returns false, then no further methods in the plugin will be called
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 为每一个 XxxDao.java 生成 SELECT_BY_QUERY_OBJECT 方法
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        LOGGER.info("generate mapper interface for: {}, named: {}",
                introspectedTable.getBaseRecordType(), interfaze.getType().toString());

        // import
        interfaze.addImportedType(new FullyQualifiedJavaType(PageQueryObject.class.getName()));

        Method method = new Method(SELECT_BY_QUERY_OBJECT);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(PageQueryObject.class.getSimpleName()),"queryObject"));

        FullyQualifiedJavaType typeList = FullyQualifiedJavaType.getNewListInstance();
        typeList.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));  // 泛型
        method.setReturnType(typeList);

        interfaze.addMethod(method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return true;
    }


    /**
     * 为每一个 sqlMap.xml 生成 id 为 SELECT_BY_QUERY_OBJECT 的 select 标签
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        LOGGER.info("generate sqmMap.xml for {}", introspectedTable.getBaseRecordType());

        XmlElement element = new XmlElement("select");
        element.addAttribute(new Attribute("id", SELECT_BY_QUERY_OBJECT));
        element.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        context.getCommentGenerator().addComment(element); // 增加自动创建的注释

        StringBuilder sb = new StringBuilder("select ");
//        TODO 实现sql
        element.addElement(new TextElement(sb.toString()));

        document.getRootElement().addElement(element);
        return true;
    }
}
