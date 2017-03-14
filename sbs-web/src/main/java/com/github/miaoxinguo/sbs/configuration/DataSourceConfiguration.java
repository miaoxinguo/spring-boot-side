package com.github.miaoxinguo.sbs.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.miaoxinguo.mybatis.plugin.DataSourceType;
import com.github.miaoxinguo.mybatis.plugin.RoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置类
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Resource
    private Environment env;

    /**
     * 路由数据源
     */
    @Bean(name = "dataSource", autowire = Autowire.BY_NAME)
    @Primary
    public DataSource dataSource() {
        RoutingDataSource dataSource = new RoutingDataSource();

        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceType.MASTER, this.dataSourceMaster());
        map.put(DataSourceType.SLAVE, this.dataSourceSlave());

        dataSource.setTargetDataSources(map);
        dataSource.setDefaultTargetDataSource(dataSourceMaster());
        return this.dataSourceMaster();
    }

    /**
     * 主库
     */
    @Bean(name = "master", autowire = Autowire.BY_NAME)
    public DataSource dataSourceMaster() {
        LOGGER.info("master db url = {}", env.getProperty("datasource.master.url"));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("datasource.master.url"));
        dataSource.setUsername(env.getProperty("datasource.master.username"));
        dataSource.setPassword(env.getProperty("datasource.master.password"));

        return dataSource;
    }

    /**
     * 只读从库-1
     */
    @Bean(name = "slave", autowire = Autowire.BY_NAME)
    public DataSource dataSourceSlave() {
        LOGGER.info("slave db url = {}", env.getProperty("datasource.master.url"));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("datasource.master.url"));
        dataSource.setUsername(env.getProperty("datasource.master.username"));
        dataSource.setPassword(env.getProperty("datasource.master.password"));
        return dataSource;
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
