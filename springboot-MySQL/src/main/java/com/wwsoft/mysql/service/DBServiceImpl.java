package com.wwsoft.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wwsoft.mysql.api.DBService;
import com.wwsoft.mysql.persistence.entities.Persons;
import com.wwsoft.mysql.persistence.repositores.PersonsRepository;

@Service
public class DBServiceImpl implements DBService {
	@Autowired
	private PersonsRepository dbRepo;
	
	public Persons saveDataEntry(Persons entity) {
		Persons result = dbRepo.save(entity);
		return result;
	}
	
	public Persons getDataEntry(Long id) throws Exception {
		return dbRepo.findOne(id);
	}
	
	public Persons updateDataEntry(Persons entity) throws Exception {
		Persons result = dbRepo.save(entity);
		return result;

	}
	
	public void deleteDataEntry(Persons entity) throws Exception {
		dbRepo.delete(entity);
	}
	
	public void deleteDataEntryByFirstName(String firstName) {
		dbRepo.deletePersonsByFirstName(firstName);
	}
}
