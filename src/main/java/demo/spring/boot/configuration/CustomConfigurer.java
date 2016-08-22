package demo.spring.boot.configuration;

import demo.spring.boot.resolver.TestArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 更改spring boot自动配置的内容
 */
@Configuration
public class CustomConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 增加自定义参数解析器Task
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TestArgumentResolver());
    }

}