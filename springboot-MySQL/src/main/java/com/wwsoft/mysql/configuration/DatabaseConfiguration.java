package com.wwsoft.mysql.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfiguration {	
	protected static Logger logger = Logger.getLogger("Configer");
	
	public static String appPropLocation;
	
	@Autowired
	ApplicationConfiguration ac;
	
	@PostConstruct
	public void init() {
		logger.info("******************  Post bean processing-init");
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("******************  Post bean processing-destroy");
	}
	
	@Bean
	public DataSource dataSource() throws IOException {
	    return DataSourceBuilder
	        .create()
	        .username(ac.getUsername())
	        .password(getSecurePassword())
	        .url(ac.getUrl())
	        .driverClassName("com.mysql.jdbc.Driver")
	        .build();
	}
	
	private String getSecurePassword() throws IOException {
		logger.info("*********************** getSecurePassword starting...");
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	    encryptor.setPassword("mysecret");
	    Properties props = new EncryptableProperties(encryptor);
	    props.load(new FileReader(appPropLocation));
	    logger.info("*********************** uname:" + props.getProperty("spring.datasource.username"));
	    logger.info("*********************** getSecurePassword returning:" + props.getProperty("spring.datasource.password"));
	    return props.getProperty("spring.datasource.password");
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws IOException {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan(ac.getPackagesToScan());
	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", ac.getDialect());
	    if ( "true".equals(ac.getShowsql()))
	      hibernateProperties.put("hibernate.show_sql", true);
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);

	    return sessionFactoryBean;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
	    LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	    em.setDataSource(dataSource());
	    em.setPackagesToScan(ac.getPackagesToScan()); 
	    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    em.setJpaVendorAdapter(vendorAdapter);
	    em.setJpaProperties(additionalProperties());
	 
	    return em;
	 }
	 
	 Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", ac.getDialect());
		properties.setProperty("hibernate.show_sql", "true");
		        
		return properties;
	 }
	 
	 @Bean
	 @Profile("jpa")
	 public PlatformTransactionManager transactionManager() throws IOException {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());	  
	    return transactionManager;
	 }
	  
	 @Bean
	 public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	     return new PersistenceExceptionTranslationPostProcessor();
	 }
	 
	 @Bean
	 @Profile("hibernate")
	 public PlatformTransactionManager hibernateTransactionManager() throws IOException {
	     final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	     transactionManager.setSessionFactory(sessionFactory().getObject());
	     return transactionManager;
	 }
}


