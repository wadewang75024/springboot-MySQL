package com.wwsoft.mysql.api;

import com.wwsoft.mysql.persistence.entities.Persons;

public interface PersonsDAO {
	Persons getWithQuery(long id) throws Exception;
	Persons getWithCriteria(long id) throws Exception;
}
