package com.wwsoft.mysql.persistence.daos;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.api.PersonsDAO;
import com.wwsoft.mysql.persistence.entities.Persons;

@Component
public class PersonsDAOImpl implements PersonsDAO {
	private static Logger logger = Logger.getLogger("PersonsDAOImpl");
	
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@Transactional
	public Persons getWithQuery(long id) throws Exception {
		logger.info("PersonsDAOImpl getWithQuery starts...: " + sessionFactory);
		/**
		 * Note that this query without "JOIN FETCH or LEFT JOIN FETCH" will cause
		 * Hibernate to run 1+1 queries, meaning that one query for persons and 
		 * one query for the associated contacts.
		 */
		Query query = getSession().createQuery("from Persons where id=:id");
		query.setParameter("id", id);
		
		return (Persons) query.list().get(0);
	}
	
	@Override
	@Transactional
	public Persons getWithCriteria(long id) throws Exception {
		logger.info("PersonsDAOImpl getWithCriteria starts...: " + sessionFactory);
		
		/**
		 * With Criteria and of course when fetchType=EAGER, there will be one query executed.
		 */
		Persons person = (Persons) getSession()
		.createCriteria(Persons.class)
		.add(Restrictions.eq("id", id))
		.uniqueResult();
		return person;
	}
}
