package com.ruyang.urlshortener.config;

import com.ruyang.urlshortener.filter.AuthFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(){
        FilterRegistrationBean<AuthFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/api/url/shorten");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
