package com.wwsoft.mysql.api;


import org.springframework.data.repository.query.Param;

import com.wwsoft.mysql.data.Persons;

public interface DBService {
	public Persons getDataEntry(Long id) throws Exception;
	public Persons saveDataEntry(Persons entity) throws Exception;
	public Persons updateDataEntry(Persons entity) throws Exception;
	public void deleteDataEntry(Persons entity) throws Exception;
	public void deleteDataEntryByFirstName(String firstName);
}
