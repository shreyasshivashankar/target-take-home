package com.myretail.productapi.server;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean masterOfTheUniverseFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenValidityFilter());
        registration.addUrlPatterns("/api/v1/");
        registration.setName("tokenFilter");
        registration.setOrder(1);
        return registration;
    }
}
