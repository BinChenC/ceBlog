package com.cb.myblog.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author cb
 * @version 1.0
 * @date 2020/3/25 14:46
 */
@Configuration
public class CorsConfig {

    private UrlBasedCorsConfigurationSource source(){
        // 注册CORS过滤器
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    private CorsConfiguration corsConfiguration(){
        CorsConfiguration config = new CorsConfiguration();
        config.addExposedHeader("Authorization");
        // 是否支持安全证书
        config.setAllowCredentials(true);
        // 允许任何域名使用
        config.addAllowedOrigin("*");
        // 允许任何头
        config.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        config.addAllowedMethod("*");
        // 预检请求的有效期，单位为秒。
        config.setMaxAge(3600L);
        return config;
    }

    /**
     * Cors support
     */
    @Bean
    public FilterRegistrationBean myCorsConfig() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source()));
        bean.setOrder(0);
        return bean;
    }

}