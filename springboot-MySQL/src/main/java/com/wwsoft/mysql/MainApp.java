package com.wwsoft.mysql;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wwsoft.mysql.configuration.Configer;
import com.wwsoft.mysql.dtos.PersonEmail;

/**
 * @author WWANG 
 */ 
@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@SpringBootApplication
@ComponentScan("com.wwsoft.mysql")
@EnableJpaRepositories(basePackages = "com.wwsoft.mysql.persistence.repositores") 
public class MainApp { 
	
	ConfigurableApplicationContext context;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	protected static Logger logger = Logger.getLogger("MainApp");
	
	@Autowired
	private BusinessApplicationHibernate businessApplicationH;
	@Autowired
	private BusinessApplicationJPA businessApplicationJ;
	
	@PostConstruct
	public void init() {
		logger.info("******************  Post bean processing-init");
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("******************  Post bean processing-destroy");
	}
	
    public static void main(final String[] args) throws Exception { 
    	    	
    	logger.info("*******************  Main start......");
    	if ( args.length != 1) {
    		System.out.println("Usage java <application.properties>");
    		System.exit(-1);
    	}
    	Configer.appPropLocation = args[0];
    	ConfigurableApplicationContext ctx = SpringApplication.run(MainApp.class, args);
    	MainApp mainObj = ctx.getBean(MainApp.class);
    	mainObj.context = ctx;
    	
    	SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
    	logger.info("*******************  sessionFactory: " + sessionFactory);
    	
    	logger.info("*******************  Start business processing......");
    	logger.info("*******************  activeProfile " + mainObj.activeProfile);
    	
    	if ( mainObj.activeProfile.equals("jpa") ) {
    		mainObj.businessApplicationJ.start();
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("*********************** Run with Spring JPA - BusinessApplicationJPA ");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    	}
    	else {
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("*********************** Run with Hibernate - BusinessApplicationHibernate ");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		logger.info("****************************************************************************");
    		mainObj.businessApplicationH.start();
    	}
    	
    	/**********************************************************************************
    	 * Try out the concept in which accessing spring beans from a non-bean class
    	 **********************************************************************************/
    	PersonEmail pe = new PersonEmail();
    	pe.context = ctx;
    	pe.tryAccessSpringBeans();
    	
    	/**
    	 * For Spring to call @PreDestroy callback method when you application shuts down, 
    	 * you have to add a shutdown hook and close the application context it in. 
    	 * You could attach the hook to JVM using Runtime.getRuntime().addShutdownHook(Thread) 
    	 * or to Jetty if it provides such an API. Here is how you'd do it with JVM shutdown hook:
    	 */
    	ctx.registerShutdownHook();
  
		logger.info("******************  Main ends!.");
    } 
}

