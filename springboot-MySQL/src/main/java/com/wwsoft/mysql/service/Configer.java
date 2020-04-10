package com.wwsoft.mysql.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.wwsoft.mysql.ApplicationConfiguration;

@Configuration
public class Configer {	
	protected static Logger logger = Logger.getLogger("Configer");
	
	public static String appPropLocation;
	
	@Autowired
	ApplicationConfiguration ac;
	
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
}


