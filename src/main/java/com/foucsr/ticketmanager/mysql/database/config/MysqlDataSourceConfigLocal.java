package com.foucsr.ticketmanager.mysql.database.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author FocusR
 * 
 */
@Configuration
@EnableJpaRepositories(
		basePackages = "com.foucsr.ticketmanager.mysql.database.repository",
        entityManagerFactoryRef = "mySqlEntityManagerFactory",
        transactionManagerRef = "mySqlTransactionManager"
)
public class MysqlDataSourceConfigLocal
{
	@Autowired
	private Environment env;
	@Profile("dev")	
    @Bean
    @ConfigurationProperties(prefix="datasource.mysql")
    public DataSourceProperties securityDataSourceProperties() {
        return new DataSourceProperties();
    }
	@Profile("dev")	
    @Bean
    public DataSource securityDataSource() {
        DataSourceProperties securityDataSourceProperties = securityDataSourceProperties();
		return DataSourceBuilder.create()
        			.driverClassName(securityDataSourceProperties.getDriverClassName())
        			.url(securityDataSourceProperties.getUrl())
        			.username(securityDataSourceProperties.getUsername())
        			.password(securityDataSourceProperties.getPassword())
        			.build();
    }
	@Profile("dev")	
    @Bean
    public PlatformTransactionManager mySqlTransactionManager()
    {
        EntityManagerFactory factory = mySqlEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }
	@Profile("dev")	
    @Bean
    public LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(securityDataSource());
        factory.setPackagesToScan(new String[]{"com.foucsr.ticketmanager.mysql.database.model"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);
        
        return factory;
    }
    
 /*   @Bean
	public DataSourceInitializer securityDataSourceInitializer() 
	{
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(securityDataSource());
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("security-data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("datasource.mysql.initialize", Boolean.class, false));
		return dataSourceInitializer;
	}*/
    
    
}
