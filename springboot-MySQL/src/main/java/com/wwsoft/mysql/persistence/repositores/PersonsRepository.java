package com.wwsoft.mysql.persistence.repositores;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.persistence.entities.Persons;

import org.springframework.data.repository.query.Param;

@Repository
public interface PersonsRepository extends CrudRepository<Persons, Long> {
	@Query("delete from Persons d where d.firstName=:firstName ")
	@Modifying
	@Transactional
	public void deletePersonsByFirstName(@Param("firstName")String firstName);
}
