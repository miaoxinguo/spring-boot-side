package io.github.miaoxinguo.sbs.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;

import java.util.List;

/**
 * 自动生成代码的插件
 */
public class MapperPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 当 doc 生成后执行
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 每个元素后插入空行
        List<Element> elements = document.getRootElement().getElements();
        for (int index = 1; index < elements.size(); index+=2) {
            elements.add(index, new TextElement(""));
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
