package io.github.miaoxinguo.sbs.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
<<<<<<< HEAD
 * 生成 entity、dao 类和映射器 xml 文件
=======
 * 生成 repository.xml 文件
>>>>>>> 3b1c43779cbc8fee873ddda9adf430a2c3a62c20
 */
public class MapperGenerator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();

<<<<<<< HEAD
        ConfigurationParser cp = new ConfigurationParser(warnings);
        File configFile = new File(MapperGenerator.class.getResource("generatorConfig.xml").getPath());
=======
        File configFile = new File(MapperGenerator.class.getResource("/generatorConfig.xml").getPath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
>>>>>>> 3b1c43779cbc8fee873ddda9adf430a2c3a62c20
        Configuration config = cp.parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(true);

        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
