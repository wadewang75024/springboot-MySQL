package com.wwsoft.mysql.persistence.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wwsoft.mysql.persistence.entities.Persons;

import org.springframework.data.repository.query.Param;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, Long> {
	@Query("delete from Persons d where d.lastName=:lastName ")
	@Modifying
	@Transactional
	public void deletePersonsByLastName(@Param("lastName")String lastName);
	
	@Transactional
	public List<Persons> findPersonsWithContacts(@Param("personId") long id);
	
	@Modifying
	@Transactional
	@Query("update PersonContacts pc set pc.email=:newEmail where pc.person.id=:pid and pc.type=:type")
	public int updateEmail(@Param("pid")long personId, @Param("type")char type, @Param("newEmail")String newEmail) throws Exception;
}
