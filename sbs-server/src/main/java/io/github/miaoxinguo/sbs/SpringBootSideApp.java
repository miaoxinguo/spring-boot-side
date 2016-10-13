package io.github.miaoxinguo.sbs;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class})
public class SpringBootSideApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootSideApp.class);

    /**
     * 应用入口
     */
    public static void main(String[] args) {
        LOGGER.info("server starting ...");
        SpringApplication.run(SpringBootSideApp.class, args);

        LOGGER.info("server start finished");
    }
}
