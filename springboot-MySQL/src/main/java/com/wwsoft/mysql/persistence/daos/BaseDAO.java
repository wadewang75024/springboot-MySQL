package com.wwsoft.mysql.persistence.daos;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseDAO {
	protected static Logger logger = Logger.getLogger("BaseDAO");
	
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	
	protected EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
