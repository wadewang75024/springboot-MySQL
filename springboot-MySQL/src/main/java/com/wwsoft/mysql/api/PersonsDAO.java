package com.wwsoft.mysql.api;

import java.util.List;

import com.wwsoft.mysql.dtos.PersonEmail;
import com.wwsoft.mysql.persistence.entities.Persons;

public interface PersonsDAO {
	Persons getWithQuery(long id) throws Exception;
	Persons getWithNamedQuery(long id) throws Exception;
	Persons getWithCriteria(long id) throws Exception;
	Persons getWithSessionGet(long id) throws Exception;
	Persons getWithSessionLoad(long id) throws Exception;
	List<PersonEmail> getWithNativeNamedQuery() throws Exception;
	
	int updateEmail(long personId, char type, String newEmail) throws Exception;
	
	long add(Persons newPerson) throws Exception ;
	void remove(long id) throws Exception;
}
