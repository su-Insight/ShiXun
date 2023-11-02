package com.example.insight.config;

import com.example.insight.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/30 下午8:48
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // registry.addInterceptor(jwtInterceptor())
        //         .addPathPatterns("/**")    // 拦截所有请求，通过判断token是否合法来决定是否需要登录
        //         .excludePathPatterns("/user/login", "user/register", "/**/export", "/**/import", "/swagger-ui/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
