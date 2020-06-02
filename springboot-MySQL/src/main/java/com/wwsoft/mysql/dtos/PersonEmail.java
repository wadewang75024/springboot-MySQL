package com.wwsoft.mysql.dtos;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.wwsoft.mysql.persistence.entities.Persons;

public class PersonEmail {
	protected static Logger logger = Logger.getLogger("PersonEmail");
	public ConfigurableApplicationContext context;
	
	public void tryAccessSpringBeans() {
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
    	logger.info("*******************  tryAccessSpringBeans in non spring bean class *******************");
    	Session session = sessionFactory.openSession();
    	
    	Persons p = session.load(Persons.class, 1l);
    	logger.info("*******************  " +p.getLastName());
    	logger.info("*******************  tryAccessSpringBeans in non spring bean class *******************");
	}
	
	private long personId;
	private String lastName;
	private String firstName;
	
	private long contactId;
	private String email;
	
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public long getContactId() {
		return contactId;
	}
	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
