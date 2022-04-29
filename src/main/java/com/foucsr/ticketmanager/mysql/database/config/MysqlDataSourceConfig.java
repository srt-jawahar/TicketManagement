package com.foucsr.ticketmanager.mysql.database.config;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@PropertySource("classpath:application-dev.properties")
@EnableJpaRepositories(
		basePackages = "com.foucsr.ticketmanager.mysql.database.repository",
        entityManagerFactoryRef = "mySqlEntityManagerFactory",
        transactionManagerRef = "mySqlTransactionManager"
)
public class MysqlDataSourceConfig {
	@Profile("prod")
	@Bean
	@Primary
	public DatabaseProperties databaseProperties() {
		return new DatabaseProperties();
	}

	/*@Bean
	@Primary
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatWebServer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();

				// resource.setType(DataSource.class.getName());
				resource.setType("org.apache.tomcat.jdbc.pool.DataSource");
				// resource.setName(env.getProperty("jndiName"));
				resource.setName(databaseProperties().getJndiName());
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				// resource.setProperty("driverClassName", env.getProperty("driverClassName"));
				// resource.setProperty("url", env.getProperty("jdbcUrl"));
				// resource.setProperty("password", env.getProperty("username"));
				// resource.setProperty("username", env.getProperty("password"));
				resource.setProperty("driverClassName", databaseProperties().getDriverClassName());
				resource.setProperty("url", databaseProperties().getUrl());
				resource.setProperty("password", databaseProperties().getUsername());
				resource.setProperty("username", databaseProperties().getPassword());

				context.getNamingResources().addResource(resource);
			}
		};
	}*/

//	@Bean(name = "datasource.mysql")
	@Profile("prod")
	@Primary
	//@ConfigurationProperties(prefix="datasource.mysql")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		// bean.setJndiName("java:comp/env/" + env.getProperty("jndiName"));
		bean.setJndiName("java:comp/env/" + databaseProperties().getJndiName());
		bean.setProxyInterface(DataSource.class);
		// bean.setResourceRef(true);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
	@Profile("prod")
	@Bean
	@Primary
	public EntityManagerFactory mySqlEntityManagerFactory() throws SQLException, IllegalArgumentException, NamingException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(false);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.foucsr.ticketmanager.mysql.database.model");
		factory.setDataSource(jndiDataSource());
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	@Profile("prod")
	@Bean
	@Primary
	public PlatformTransactionManager mySqlTransactionManager()
			throws SQLException, IllegalArgumentException, NamingException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(mySqlEntityManagerFactory());
		return txManager;
	}

}
