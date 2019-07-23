package com.ucx.training.shop.configuration;

import com.ucx.training.shop.security.JwtFilter;
import com.ucx.training.shop.security.PassThroughFilter;
import com.ucx.training.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfiguration {

    @Value("${jwt.filter.enabled}")
    private String applyJwtFilter;

    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        if (JwtUtil.applyJwtFilter(applyJwtFilter)) {
            filterRegistrationBean.setFilter(new JwtFilter());
            filterRegistrationBean.addUrlPatterns("/*");
        } else {
            filterRegistrationBean.setFilter(new PassThroughFilter());
        }
        return filterRegistrationBean;
    }
}
