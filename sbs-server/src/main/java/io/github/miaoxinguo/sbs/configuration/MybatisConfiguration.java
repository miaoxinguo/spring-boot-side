package io.github.miaoxinguo.sbs.configuration;

import io.github.miaoxinguo.sbs.entity.BaseEntity;
import io.github.miaoxinguo.sbs.repository.GenericRepository;
import io.github.miaoxinguo.sbs.repository.MarkerRepository;
import io.github.miaoxinguo.sbs.vfs.SpringBootVFS;
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
 * Mybatis 配置类
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
@MapperScan(basePackageClasses = MarkerRepository.class, markerInterface = GenericRepository.class)
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

        // 设置配置文件路径（可以把配置文件的内容都用代码写在这里，不用配置文件）
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

        // 添加 entity 的类型
        sessionFactory.setTypeAliasesSuperType(BaseEntity.class);

        // 添加 map.xml 的目录（路径也可以写在 application.yml 里）
        sessionFactory.setMapperLocations(resolver.getResources("classpath:sqlMap/*Mapper.xml"));

        // 添加 typeHandler
        sessionFactory.setTypeHandlersPackage("io.github.miaoxinguo.sbs.typeHandler");

        return sessionFactory.getObject();
    }
}
