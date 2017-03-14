package com.github.miaoxinguo;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成 entity、dao 类和映射器 xml 文件
 */
public class MapperGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperGenerator.class);

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        File configFile = new File(MapperGenerator.class.getResource("/generatorConfig.xml").getPath());
        Configuration config = cp.parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(true);  // parameter = is overwrite

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        warnings.forEach(LOGGER::warn);
    }
}
