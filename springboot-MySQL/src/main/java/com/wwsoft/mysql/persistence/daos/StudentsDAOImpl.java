package com.wwsoft.mysql.persistence.daos;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wwsoft.mysql.api.StudentsDAO;
import com.wwsoft.mysql.persistence.entities.Students;

@Service
public class StudentsDAOImpl extends BaseDAO implements StudentsDAO {
	
	@Override
	@Transactional
	public long addWithSave(Students newStudent) throws Exception {
		logger.info("StudentsDAOImpl addWithSave starts...: " );
		getSession().save(newStudent);
		return newStudent.getId();	 
	}
	
	@Override
	@Transactional
	public Students getStudentWithIdUsingSessionGet(long studentId) throws Exception {
		logger.info("StudentsDAOImpl getStudentWithIdUsingSessionGet starts...: " );
		Students student = getSession().get(Students.class, studentId);
		return student;	 
	}
	
	@Override
	@Transactional
	public Students getStudentWithIdUsingSessionLoad(long id) throws Exception {		
		logger.info("StudentsDAOImpl getStudentWithIdUsingSessionLoad starts...: " );
		Students found = openSession().load(Students.class, id);
		return found;	
	}
	
	@Override
	@Transactional
	public void remove(long id) throws Exception {
		logger.info("PersonsDAOImpl remove starts...: " );
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Students s = em.find(Students.class, id);
		em.remove(s);
	    em.getTransaction().commit();
	}
}
