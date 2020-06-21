package com.wwsoft.mysql.persistence.daos;

import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.api.StudentCourseDAO;
import com.wwsoft.mysql.persistence.entities.StudentCourse;

@Service
public class StudentCourseDAOImpl extends BaseDAO implements  StudentCourseDAO {
	
	@Override
	@Transactional
	public void addWithSave(StudentCourse studentCourse) throws Exception {
		logger.info("StudentCourseDAOImpl addWithSave starts...: " );
		getSession().save(studentCourse);
	}
	
	@Override
	@Transactional
	public int updateScore(long studentId, long courseId, byte score) {
		logger.info("StudentCourseDAOImpl updateScore starts...: " );
		Query query = getSession().createQuery("update StudentCourse sc " +
											   "set sc.score = :score" +
	                                           " where sc.studentId=:sid" +
											   " and sc.courseId = :cid");
		query.setByte("score", score);
		query.setParameter("sid", studentId);
		query.setParameter("cid", courseId);
		int result = query.executeUpdate();
		
		return result;
	}
}
