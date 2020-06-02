package com.wwsoft.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wwsoft.mysql.api.DummyService;
import com.wwsoft.mysql.persistence.entities.PersonContacts;
import com.wwsoft.mysql.persistence.entities.Persons;
import com.wwsoft.mysql.persistence.repositores.PersonsRepository;

@Component
public class BusinessApplicationJPA {
	private static Logger logger = Logger.getLogger("BusinessApplicationJPA");
	
	DummyService dummyService;
	
	@Autowired
	private PersonsRepository dbRepo;
	
	/**
	 * Constructor-based injection
	 * @param dummyService
	 */
	@Autowired
	public BusinessApplicationJPA(DummyService dummyService) {
		logger.info("******************  Constructor-based injection");
		this.dummyService =dummyService;
	}
	
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
		
		logger.info("****************************************************************************");	
		logger.info("******************  Use findById...");
		try {
			Optional<Persons> person = dbRepo.findById(1l);		
			if (person.isPresent()) {
				logger.info(person.get().getFirstName() + "-" + person.get().getLastName() + "-");
				
				/**
				 * The following will FAIL with LAZY loading with lazy initialization exception!!!!!
				 */
				person.get().getPersonContacts().forEach(pc->logger.info(person.get().getPersonContacts().get(0).getEmail()));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use findById complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use findPersonsWithContacts...");
		try {
			List<Persons> persons = dbRepo.findPersonsWithContacts(1l);	
			persons.forEach(p-> logger.info(p.getFirstName() +"-" + p.getLastName()));
			persons.forEach( p-> { p.getPersonContacts().forEach( pc -> { logger.info(pc.getEmail()) ;} );});
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use findPersonsWithContacts complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  updateEmail...");
		try {
			int result = dbRepo.updateEmail(35l, 'U', "newemail1@gmail.com");	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  updateEmail complete!!!");
		logger.info("****************************************************************************");
		
		
		logger.info("****************************************************************************");
		logger.info("******************  Save and Update, and Save...");
		Persons np = null;
		try {
			/******************************************************************************
			 * JPA will generate 1 + N inserts
			 * 		- one for Persons and 
			 * 		- N for the N contacts.
			 * Note that JPA it will insert the parent record first and the child records.
			 ******************************************************************************/
			Persons newPerson = new Persons();
			newPerson.setFirstName("New1");
			newPerson.setLastName("Person1");
			newPerson.setAddress("1000 New St.");
			newPerson.setCity("1000 New St.");
			
			PersonContacts newPC = new PersonContacts();
			newPC.setPerson(newPerson);
			newPC.setType('T');
			newPC.setEmail("newperson1@email.com");
			PersonContacts newPC1 = new PersonContacts();
			newPC1.setPerson(newPerson);
			newPC1.setType('T');
			newPC1.setEmail("newperson1@email.com");
			List<PersonContacts> pcl = new ArrayList<>();
			pcl.add(newPC);
			pcl.add(newPC1);
			newPerson.setPersonContacts(pcl);
			np = dbRepo.save(newPerson);	
			logger.info("****************** New person ID: " + np.getId());
			
			logger.info("****************** Do a update and save it again!");
			np.setFirstName("WADE");
			dbRepo.save(np);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Save and Update, and Save!!!");
		logger.info("****************************************************************************");
		
		
		logger.info("****************************************************************************");
		logger.info("******************  deleteById... to delete the newly added person");
		try {
			/***********************************************************************************
			 * JPA will create two select queries 
			 * 		- one for Persons and 
			 * 		- one for Person Contacts,
			 * and it WILL create N+1 delete queries 
			 * 		- N delete for Person Contacts and 
			 * 		- one for Persons.
			 * Note that JPA it will delete child record first and the parent.
			 ***********************************************************************************/
			dbRepo.deleteById(np.getId());	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  deleteById complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  deletePersonsByLastName...");
		try {
			//dbRepo.deletePersonsByLastName("Test");	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  deletePersonsByLastName complete!!!");
		logger.info("****************************************************************************");
	}
}
