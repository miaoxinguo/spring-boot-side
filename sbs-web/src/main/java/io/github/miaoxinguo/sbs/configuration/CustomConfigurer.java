package io.github.miaoxinguo.sbs.configuration;

import io.github.miaoxinguo.sbs.resolver.ArgumentResolver;
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
     * 增加自定义参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ArgumentResolver());
    }

}