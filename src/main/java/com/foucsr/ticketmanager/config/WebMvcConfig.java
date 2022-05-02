package com.foucsr.ticketmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * Created by FocusR on 29-Sep-2019.
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }
    
    public void addViewControllers(ViewControllerRegistry registry)
    {
       registry.addViewController("/").setViewName("index");
       registry.addViewController("/index").setViewName("index");
       registry.addViewController("/index").setViewName("dashboard");
       registry.addViewController("/index").setViewName("openpo");
       registry.addViewController("/index").setViewName("closedpo");
       registry.addViewController("/index").setViewName("rejectedpo");
       registry.addViewController("/index").setViewName("ack");
       registry.addViewController("/index").setViewName("rescheduledpo");
       registry.addViewController("/index").setViewName("asn");
       registry.addViewController("/index").setViewName("asnshipmentstatus");
       registry.addViewController("/index").setViewName("rtv");
       registry.addViewController("/index").setViewName("invoicedetails");
       registry.addViewController("/index").setViewName("paymentdetails");
       
    }
}
