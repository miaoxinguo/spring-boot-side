package com.github.miaoxinguo.sbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootSideApp {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootSideApp.class);

    /**
     * 应用入口
     */
    public static void main(String[] args) throws Exception {
        logger.info("server starting ...");
        SpringApplication.run(SpringBootSideApp.class, args);

        logger.info("server start finished");
    }
}
