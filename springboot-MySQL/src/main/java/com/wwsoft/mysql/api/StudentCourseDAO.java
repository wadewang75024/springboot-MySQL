package com.wwsoft.mysql.api;

import com.wwsoft.mysql.persistence.entities.StudentCourse;

public interface StudentCourseDAO {
	/**
	 * Creation
	 */
	void addWithSave(StudentCourse studentCourse) throws Exception ;
	
	int updateScore(long studentId, long courseId, byte score);
}
