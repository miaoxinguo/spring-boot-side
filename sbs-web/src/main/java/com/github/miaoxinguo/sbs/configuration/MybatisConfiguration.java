package com.github.miaoxinguo.sbs.configuration;

import com.github.miaoxinguo.mybatis.plugin.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Mybatis 配置类.
 *
 * 目前的做法是加载配置文件. 也可以把配置文件的内容都用代码写在这里，不用配置文件.
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
@MapperScan(markerInterface = BaseMapper.class, basePackages = "com.github.miaoxinguo.sbs.mapper")
public class MybatisConfiguration {

    @Resource
    private DataSource dataSource;

    @Bean
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
