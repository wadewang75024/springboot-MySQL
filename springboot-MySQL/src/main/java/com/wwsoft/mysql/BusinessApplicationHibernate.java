package com.wwsoft.mysql;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.wwsoft.mysql.api.DBService;
import com.wwsoft.mysql.api.PersonsDAO;
import com.wwsoft.mysql.persistence.entities.PersonContacts;
import com.wwsoft.mysql.persistence.entities.Persons;
import com.wwsoft.mysql.service.LazyService;

@Component
public class BusinessApplicationHibernate {
	private static Logger logger = Logger.getLogger("BusinessApplicationHibernate");
	DBService dbService;
	
	PersonsDAO personDAO;
	
	@Lazy
	@Autowired
	LazyService lazyService;
	
	/**
	 * Constructor-based injection
	 * @param dbService
	 */
	@Autowired
	public BusinessApplicationHibernate(DBService dbService) {
		logger.info("******************  Constructor-based injection");
		this.dbService =dbService;
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
		
		logger.info("******************  Use getWithQuery...");
		try {
			Persons person = personDAO.getWithQuery(1);			
			System.out.println(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithQuery complete!!!");
		
		logger.info("******************  Use getWithCriteria...");
		try {
			Persons person = personDAO.getWithCriteria(1);			
			System.out.println(person.getLastName() + "-" + person.getFirstName() );
			
			Optional<List<PersonContacts>> contacts = Optional.of(person.getPersonContacts());			
			if (contacts.isPresent()) {
			     contacts.get().forEach(c->System.out.println(c.getEmail()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("******************  Use getWithCriteria complete!!!");
	}
}
