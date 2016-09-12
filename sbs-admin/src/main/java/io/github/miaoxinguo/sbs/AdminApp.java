package io.github.miaoxinguo.sbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class AdminApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminApp.class);

    /**
     * 应用入口
     */
    public static void main(String[] args) {
        LOGGER.info("admin server starting ...");
        SpringApplication.run(AdminApp.class, args);

        LOGGER.info("admin server start success");
    }
}
