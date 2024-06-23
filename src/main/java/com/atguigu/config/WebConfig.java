package com.atguigu.config;

import com.atguigu.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/**","/order/**")
                .excludePathPatterns(
                        "/user/login",
                        "user/register",
                        "/user/status",
                        "house/detail",
                        "/order/getByStatus",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );
    }
}
