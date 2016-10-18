package io.github.miaoxinguo.sbs.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.miaoxinguo.sbs.DataSourceType;
import io.github.miaoxinguo.sbs.RoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    private final Environment env;

    @Autowired
    public DataSourceConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * 路由数据源
     */
    @Bean(name = "dataSource", autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        RoutingDataSource dataSource = new RoutingDataSource();

        Map<Object, Object> map = new HashMap<>();
        map.put("dataSourceMaster", dataSourceMaster());
        map.put("dataSourceSlave1", dataSourceSlave1());
        dataSource.setTargetDataSources(map);

        dataSource.setDefaultTargetDataSource(dataSourceMaster());
        return dataSource;
    }

    /**
     * 主库
     */
    @Bean(name = DataSourceType.MASTER , autowire = Autowire.BY_NAME)
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
    @Bean(name = DataSourceType.SLAVE_1, autowire = Autowire.BY_NAME)
    public DataSource dataSourceSlave1() {
        LOGGER.info("slave-1 db url = {}", env.getProperty("datasource.master.url"));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("datasource.master.url"));
        dataSource.setUsername(env.getProperty("datasource.master.username"));
        dataSource.setPassword(env.getProperty("datasource.master.password"));

        return dataSource;
    }

    /**
     * 主库JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }


}
