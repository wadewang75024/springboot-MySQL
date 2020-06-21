package com.wwsoft.mysql.persistence.daos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.api.PersonsDAO;
import com.wwsoft.mysql.dtos.PersonEmail;
import com.wwsoft.mysql.persistence.entities.PersonContacts;
import com.wwsoft.mysql.persistence.entities.Persons;

@Component
public class PersonsDAOImpl extends BaseDAO implements PersonsDAO {
	@Override
	@Transactional
	public Persons getWithQuery(long id) throws Exception {
		logger.info("PersonsDAOImpl getWithQuery starts...: " + sessionFactory);
		/**
		 * Note that this query without "JOIN FETCH or LEFT JOIN FETCH" will cause
		 * Hibernate to run 1+1 queries, meaning that one query for persons and 
		 * one query for the associated contacts.
		 */
		Query<Persons> query = getSession().createQuery("from Persons where id=:id", Persons.class);
		query.setParameter("id", id);
		
		return query.list().get(0);
	}
	
	@Override
	public Persons getWithQueryHibernateTM(long id) {
		logger.info("PersonsDAOImpl getWithQueryHibernateTM starts...: " + sessionFactory);
		
		Session session = null;  
		Transaction tx = null;  
		Persons p = null;
		
		try {
			logger.info("*************** open session");
			session = openSession();
			
			logger.info("*************** begin txn");
			tx = session.beginTransaction();		
			Query<Persons> query = session.createQuery("from Persons where id=:id", Persons.class);
			query.setParameter("id", id);
			p =  query.list().get(0);
			
			logger.info("*************** commit txn");
			tx.commit();
		}
		catch (Exception ex ) {
			ex.printStackTrace();  
			
			logger.info("*************** rollback txn");
			tx.rollback();  
		}
		finally {
			logger.info("*************** close session");
			session.close();
		}
		return p;
	}
	
	@Override
	@Transactional
	public Persons getWithNamedQuery(long id) throws Exception {
		logger.info("PersonsDAOImpl getWithNamedQuery starts...: " + sessionFactory);
		Query<Persons> query = getSession().createNamedQuery("PersonContact_findByPersonId", Persons.class);
		query.setParameter("personId", id);
		Persons person = (Persons) query.uniqueResult();
		return person;
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
	
	/***********************************************************************
	 * Session.get and Session.load behave the same with EAGER loading,
	 * however session.get will fail with LAZY loading.  But session.load
	 * will hit the database with the second query to retrieve the missing
	 * information (in this case the contacts).
	 */
	@Override
	@Transactional
	public Persons getWithSessionGet(long id) throws Exception {		
		logger.info("PersonsDAOImpl getWithSessionGet starts...: " );
		Persons found = getSession().get(Persons.class, id);
		return found;	
	}
	
	@Override
	@Transactional
	public Persons getWithSessionLoad(long id) throws Exception {		
		logger.info("PersonsDAOImpl getWithSessionLoad starts...: " );
		Persons found = openSession().load(Persons.class, id);
		return found;	
	}
	/**
	 * Session.get and Session.load behave the same with EAGER loading,
	 * however session.get will fail with LAZY loading.  But session.load
	 * will hit the database with the second query to retrieve the missing
	 * information (in this case the contacts).
	 ************************************************************************/
	
	
	@Override
	@Transactional
	public List<PersonEmail> getWithNativeNamedQuery() throws Exception {	
		logger.info("PersonsDAOImpl getWithNativeNamedQuery starts...: " );
		NativeQuery query = getSession().getNamedNativeQuery("findAllPersonsWithContacts_native");
		
		List<PersonEmail> peList = new ArrayList<>();
		List<Object[]> result = query.list();
		for (int i=0; i<result.size(); ++i) {
			PersonEmail pe= new PersonEmail();
			Object[] row = result.get(i);
			pe.setPersonId(((BigInteger) row[0]).longValue());
			pe.setFirstName((String) row[1]);
			pe.setLastName((String)row[2]);
			pe.setContactId(((BigInteger) row[3]).longValue());
			pe.setEmail((String)row[4]);
			peList.add(pe);			
		}
		
		return peList;	
	}
	
	@Override
	@Transactional
	public int updateEmail(long personId, char type, String newEmail) throws Exception {
		logger.info("PersonsDAOImpl updateEmail starts...: " );
		Query query = getSession().createQuery("update PersonContacts pc set pc.email = :newEmail" +
	                                                           " where pc.type=:type" +
											   				   " and pc.person.id = :id");
		query.setParameter("newEmail", newEmail);
		query.setParameter("id", personId);
		query.setParameter("type", type);
		int result = query.executeUpdate();
		
		return result;
	}
	
	@Override
	@Transactional
	public long addWithSave(Persons newPerson) throws Exception {		
		logger.info("PersonsDAOImpl addWithSave starts...: " );
		getSession().save(newPerson);
		return newPerson.getId();	
	}
	
	@Override
	@Transactional
	public void addWithPersist(Persons newPerson) throws Exception {
		logger.info("PersonsDAOImpl addWithPersist starts...: " );
		getSession().persist(newPerson);
	}
	
	@Override
	@Transactional
	public Persons addWtihMerge(Persons newPerson) throws Exception {
		logger.info("PersonsDAOImpl addWtihMerge starts...: " );
		Persons p = (Persons) getSession().merge(newPerson);
		return p;
	}
	
	@Override
	@Transactional
	public void update(Persons p) throws Exception {
		logger.info("PersonsDAOImpl update starts...: " );
		getSession().update(p);
	}
	
	@Override
	@Transactional
	public 
	void saveOrUpdate(Persons p) throws Exception {
		logger.info("PersonsDAOImpl update starts...: " );
		getSession().saveOrUpdate(p);
	}
	
	@Override
	@Transactional
	public void remove(long id) throws Exception {
		logger.info("PersonsDAOImpl remove starts...: " );
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Persons p = em.find(Persons.class, id);
		em.remove(p);
	    em.getTransaction().commit();
	}
}
