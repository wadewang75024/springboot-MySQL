package com.wwsoft.mysql.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.api.DBService;
import com.wwsoft.mysql.data.Persons;
import com.wwsoft.mysql.repo.DBRepository;

@Service
public class DBServiceImpl implements DBService {
	@Autowired
	private DBRepository dbRepo;
	
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
