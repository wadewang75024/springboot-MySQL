package com.wwsoft.mysql.api;

import com.wwsoft.mysql.persistence.entities.Students;

public interface StudentsDAO {
	/**
	 * Creation
	 */
	long addWithSave(Students newStudent) throws Exception ;
	
	/**
	 * Select
	 */
	Students getStudentWithIdUsingSessionGet(long id) throws Exception;
	Students getStudentWithIdUsingSessionLoad(long id) throws Exception;
	
	/**
	 * Delete
	 */
	void remove(long id) throws Exception;
}
