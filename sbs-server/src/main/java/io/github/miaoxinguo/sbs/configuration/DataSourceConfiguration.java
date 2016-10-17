package io.github.miaoxinguo.sbs.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

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
     * 主数据源
     */
    @Bean(name = "dataSource", autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        DataSourceBuilder.create();
        LOGGER.info("master db url = {}", env.getProperty("datasource.master.url"));

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
