package com.github.miaoxinguo.sbs.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Mybatis 配置类.
 *
 * 目前的做法是加载配置文件. 也可以把配置文件的内容都用代码写在这里，不用配置文件.
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
@MapperScan(basePackages = "com.github.miaoxinguo.sbs.mapper")
public class MybatisConfiguration {

    private final DataSource dataSource;

    @Autowired
    public MybatisConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // 设置配置文件路径（）
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

        // 添加 mapper.xml 的目录
        sessionFactory.setMapperLocations(resolver.getResources("classpath:sqlMap/*Mapper.xml"));

        return sessionFactory.getObject();
    }
}
