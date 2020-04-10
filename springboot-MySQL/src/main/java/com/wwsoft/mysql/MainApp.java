package com.wwsoft.mysql;

import java.util.Date;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wwsoft.mysql.api.DBService;
import com.wwsoft.mysql.data.Persons;
import com.wwsoft.mysql.service.Configer;

/**
 * @author WWANG 
 */ 
@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@SpringBootApplication
@ComponentScan("com.wwsoft.mysql")
@EnableJpaRepositories(basePackages = "com.wwsoft.mysql.repo") 
public class MainApp { 
	
	ConfigurableApplicationContext context;
	
	protected static Logger logger = Logger.getLogger("MainApp");
	
	@Autowired
	DBService dbService;
	
	private Persons saveDataEntity(String firstName) {
		logger.info("******************  Save DataEntity");
		Persons data = null;
		try {
			data = new Persons();
			data.setFirstName(firstName);
			data.setLastName("Test");
			data.setAddress("This is a test");
			data.setCity("Test");
			Date time = new Date();
			logger.info("**************** time: " + time.toString());
			data.setCreateDate(time);
			data.setUpdateDate(time);
			dbService.saveDataEntry(data);
			logger.info("Save DataEntity complete");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private Persons retrieveDataEntiry(Long pid) {
		logger.info("****************** retrieving a DataEntity with pid : " + pid);
		Persons result=null;
		try {				  	
			result = dbService.getDataEntry(pid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Persons updateDataEntiry(Persons person) {
		logger.info("****************** updating a DataEntity with pid : " + person.getId());
		Persons result=null;
		try {				  	
			person.setFirstName("Charles");
			result = dbService.updateDataEntry(person);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Persons deleteDataEntiry(Persons person) {
		logger.info("****************** deleting a DataEntity with pid : " + person.getId());
		Persons result=null;
		try {				  	
			dbService.deleteDataEntry(person);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Persons deleteDataEntiryByFirstName(String firstName) {
		logger.info("****************** deleting a DataEntity with first name : " + firstName);
		Persons result=null;
		try {				  	
			dbService.deleteDataEntryByFirstName(firstName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
    	
    	// Create a person with first name David
    	Persons david = mainObj.saveDataEntity("David");
    	logger.info("new person: " + david.getFirstName());
    	
    	// Update the person first name to Charles
    	Persons charles = mainObj.updateDataEntiry(david);
    	Persons retrievedCharles = mainObj.retrieveDataEntiry(charles.getId());
		logger.info("retrieved person: " + retrievedCharles.getFirstName());
		
		// Remove the person with id
		mainObj.deleteDataEntiry(retrievedCharles);
		
		retrievedCharles = mainObj.retrieveDataEntiry(charles.getId());
		logger.info("retrieved person: " + retrievedCharles);
		
		// Create a person with first name Wade
		Persons wade = mainObj.saveDataEntity("Wade");
		
		// Delete the person with first name
		mainObj.deleteDataEntiryByFirstName(wade.getFirstName());
  
		logger.info("******************  Main ends!.");
    } 
}

