package com.wwsoft.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.wwsoft.mysql.api.DummyService;
import com.wwsoft.mysql.api.PersonsDAO;
import com.wwsoft.mysql.dtos.PersonEmail;
import com.wwsoft.mysql.persistence.entities.PersonContacts;
import com.wwsoft.mysql.persistence.entities.Persons;
import com.wwsoft.mysql.service.LazyService;

@Component
public class BusinessApplicationHibernate {
	private static Logger logger = Logger.getLogger("BusinessApplicationHibernate");
	
	DummyService dummyService;
	
	PersonsDAO personDAO;
	
	@Lazy
	@Autowired
	LazyService lazyService;
	
	/**
	 * Constructor-based injection
	 * @param dbService
	 */
	@Autowired
	public BusinessApplicationHibernate(DummyService dummyService) {
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
	
	/**
	 * Setter-based injection
	 * @param personDAO
	 */
	@Autowired
	public void setPersonsDAO(PersonsDAO personDAO) {
		logger.info("******************  Setter-based injection");
		this.personDAO = personDAO;
	}
	
	public void start() {
		logger.info("******************  BusinessApplication startWithHibernate...");
		logger.info("******************  the lazy bean: " + lazyService);
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithQuery...");
		try {
			Persons person = personDAO.getWithQuery(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithQuery complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithNamedQuery...");
		try {
			Persons person = personDAO.getWithNamedQuery(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** Use getWithNamedQuery complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use getWithCriteria...");
		try {
			Persons person = personDAO.getWithCriteria(1);			
			logger.info(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithCriteria complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use updateEmail...");
		try {
			int i = personDAO.updateEmail(1, 'T', "dummy2@email.com");			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use updateEmail complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Use add...");
		Persons p = new Persons();
		long newId = 0;
		try {
			p.setFirstName("New");
			p.setLastName("Test");
			p.setAddress("Test Dr");
			p.setCity("Test city");
			PersonContacts pc = new PersonContacts();
			pc.setPerson(p);
			pc.setType('U');
			pc.setEmail("dummy6@gmail.com");
			List<PersonContacts> pcList = new ArrayList<>();
			pcList.add(pc);
			p.setPersonContacts(pcList);
			newId = personDAO.add(p);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use add complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  Remove id: " + newId);
		try {
			personDAO.remove(newId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** Remove complete!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithSessionGet ");
		try {
			Persons found = personDAO.getWithSessionGet(1);
			logger.info(found.getLastName() + "-" + found.getFirstName() + "-" + found.getPersonContacts().get(0).getEmail());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithSessionGet!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithSessionLoad ");
		try {
			Persons found = personDAO.getWithSessionLoad(1);
			logger.info(found.getLastName() + "-" + found.getFirstName() + "-" + found.getPersonContacts().get(0).getEmail());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithSessionLoad!!!");
		logger.info("****************************************************************************");
		
		logger.info("****************************************************************************");
		logger.info("******************  getWithNativeNamedQuery ");
		try {
			List<PersonEmail> pe = personDAO.getWithNativeNamedQuery();
			pe.forEach(pce->logger.info(pce.getLastName() + "-" + pce.getFirstName() + "-" + pce.getEmail()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("****************** getWithNativeNamedQuery!!!");
		logger.info("****************************************************************************");
	}
}
