package com.wwsoft.mysql.persistence.daos;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.api.CoursesDAO;
import com.wwsoft.mysql.persistence.entities.Courses;

@Service
public class CoursesDAOImpl extends BaseDAO implements CoursesDAO {
	
	@Override
	@Transactional
	public long addWithSave(Courses newCourse) throws Exception {
		logger.info("CoursesDAOImpl addWithSave starts...: " );
		getSession().save(newCourse);
		return newCourse.getId();	 
	}
}
