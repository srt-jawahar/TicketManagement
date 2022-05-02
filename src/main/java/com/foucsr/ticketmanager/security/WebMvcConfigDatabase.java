/**
 * 
 */
package com.foucsr.ticketmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author FocusR
 * 
 */
@Configuration
public class WebMvcConfigDatabase extends WebMvcConfigurerAdapter
{
	
	@Bean
	@Primary
    public OpenEntityManagerInViewFilter securityOpenEntityManagerInViewFilter()
    {
    	OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
    	osivFilter.setEntityManagerFactoryBeanName("mySqlEntityManagerFactory");
    	return osivFilter;
    }
	
}