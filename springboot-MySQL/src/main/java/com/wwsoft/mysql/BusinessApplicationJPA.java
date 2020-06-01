package com.wwsoft.mysql;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wwsoft.mysql.api.DBService;
import com.wwsoft.mysql.persistence.entities.Persons;

@Component
public class BusinessApplicationJPA {
	private static Logger logger = Logger.getLogger("BusinessApplicationJPA");
	@Autowired
	DBService dbService;
	
	@PostConstruct
	public void init() {
		logger.info("******************  Post bean processing-init");
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("******************  Post bean processing-destroy");
	}
	
	public void start() {
		logger.info("******************  BusinessApplication start...");
		// Create a person with first name David
    	Persons david = saveDataEntity("David");
    	logger.info("new person: " + david.getFirstName());
    	
    	// Update the person first name to Charles
    	Persons charles = updateDataEntiry(david);
    	Persons retrievedCharles = retrieveDataEntiry(charles.getId());
		logger.info("retrieved person: " + retrievedCharles.getFirstName());
		
		// Remove the person with id
		deleteDataEntiry(retrievedCharles);
		
		retrievedCharles = retrieveDataEntiry(charles.getId());
		logger.info("retrieved person: " + retrievedCharles);
		
		// Create a person with first name Wade
		Persons wade = saveDataEntity("Wade");
		
		// Delete the person with first name
		deleteDataEntiryByFirstName(wade.getFirstName());
		logger.info("******************  BusinessApplication complete!!!");
	}
	
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
}
