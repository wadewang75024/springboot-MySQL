package com.wwsoft.mysql.api;

import com.wwsoft.mysql.persistence.entities.Courses;

public interface CoursesDAO {
	/**
	 * Creation
	 */
	long addWithSave(Courses newCourse) throws Exception ;
}
