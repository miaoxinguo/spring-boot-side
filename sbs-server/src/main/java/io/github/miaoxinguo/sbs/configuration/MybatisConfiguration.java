package io.github.miaoxinguo.sbs.configuration;

import io.github.miaoxinguo.sbs.entity.BaseEntity;
import io.github.miaoxinguo.sbs.repository.GenericRepository;
import io.github.miaoxinguo.sbs.repository.MarkerRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Mybatis 配置类
 */
@Configuration
@AutoConfigureAfter({ DbConfiguration.class })
@MapperScan(basePackageClasses = MarkerRepository.class, markerInterface = GenericRepository.class)
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisConfiguration {
    private final DataSource masterDataSource;

    @Autowired
    public MybatisConfiguration(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }


    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setVfs(SpringBootVFS.class);

        // 设置配置文件路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

        // 添加 entity 的类型
        sessionFactory.setTypeAliasesSuperType(BaseEntity.class);

        // 添加 map.xml 的目录
        sessionFactory.setMapperLocations(resolver.getResources("classpath:sqlMap/*Mapper.xml"));

        //分页插件 //4
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);
        //添加插件
//        bean.setPlugins(new Interceptor[]{pageHelper});

        sessionFactory.setTypeHandlersPackage("io.github.miaoxinguo.sbs.typeHandler");


        return sessionFactory.getObject();
    }
}
