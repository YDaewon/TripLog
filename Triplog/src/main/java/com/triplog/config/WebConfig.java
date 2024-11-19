package com.triplog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.triplog.interceptor.JwtInterceptor;

@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = { "com.triplog.**.mapper" })
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/info/**")          // JWT 검사할 경로
                .excludePathPatterns("/user/login", "/user/register"); // 로그인, 회원가입 제외
    }
}