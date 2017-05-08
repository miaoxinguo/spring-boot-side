package com.github.miaoxinguo.sbs.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.miaoxinguo.sbs.properties.DataSourceMasterProperties;
import com.github.miaoxinguo.sbs.properties.DataSourceSlaveProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据库配置类
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Resource
    private Environment env;

    @Resource
    private DataSourceMasterProperties masterProperties;

    @Resource
    private DataSourceSlaveProperties slaveProperties;

    /**
     * 主库
     */
    @Bean(name = "master", autowire = Autowire.BY_NAME)
    public DataSource dataSourceMaster() {
        LOGGER.info("master properties: {}", masterProperties);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(masterProperties.getUrl());
        dataSource.setUsername(masterProperties.getUsername());
        dataSource.setPassword(masterProperties.getPassword());

        return dataSource;
    }

    /**
     * 只读从库-1
     */
    @Bean(name = "slave", autowire = Autowire.BY_NAME)
    public DataSource dataSourceSlave() {
        LOGGER.info("slave properties: {}", env.getProperty("datasource.master.url"));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slaveProperties.getUrl());
        dataSource.setUsername(slaveProperties.getUsername());
        dataSource.setPassword(slaveProperties.getPassword());
        return dataSource;
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSourceMaster());
    }
}
