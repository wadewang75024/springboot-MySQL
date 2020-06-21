package com.wwsoft.mysql.api;

import java.util.List;

import com.wwsoft.mysql.dtos.PersonEmail;
import com.wwsoft.mysql.persistence.entities.Persons;

/**
 * Hibernate Session interface provides the following methods:
 * 		- persist: The persist method is intended for adding 
 * 		  a new entity instance to the persistence context, 
 *        i.e. transitioning an instance from transient to 
 *        persistent state. It has void return type.
 *        
 *      - save: The save method is an “original” Hibernate method 
 *        that does not conform to the JPA specification. Its purpose 
 *        is basically the same as persist, but it has different 
 *        implementation details. It does return the generated identifier.
 *        
 *      - merge: The main intention of the merge method is to update 
 *        a persistent entity instance with new field values from a 
 *        detached entity instance. 
 *        merge method returns an object — it is the mergedPerson object 
 *        that was loaded into persistence context and updated, 
 *        not the person object that you passed as an argument.
 *        
 *      - update: As with persist and save, the update method is an 
 *        “original” Hibernate method that was present long before 
 *        the merge method was added.
 *        
 *      - saveOrUpdate: This method appears only in the Hibernate API and 
 *        does not have its standardized counterpart. Similar to update, 
 *        it also may be used for reattaching instances.
 *        
 *  Which one to use:
 *  	If you don't have any special requirements, as a rule of thumb, 
 *      you should stick to the persist and merge methods, 
 *      because they are standardized and guaranteed to conform to the JPA specification.
 *      They are also portable in case you decide to switch to another persistence provider, 
 *      but they may sometimes appear not so useful as the “original” Hibernate methods, save, update and saveOrUpdate.
 *
 */
public interface PersonsDAO {
	/**
	 * Retrievals
	 */
	Persons getWithQuery(long id) throws Exception;
	Persons getWithQueryHibernateTM(long id) throws Exception;
	Persons getWithNamedQuery(long id) throws Exception;
	Persons getWithCriteria(long id) throws Exception;
	Persons getWithSessionGet(long id) throws Exception;
	Persons getWithSessionLoad(long id) throws Exception;
	List<PersonEmail> getWithNativeNamedQuery() throws Exception;	
	
	/**
	 * Creation
	 */
	long addWithSave(Persons newPerson) throws Exception ;
	void addWithPersist(Persons newPerson) throws Exception ;
	Persons addWtihMerge(Persons newPerson) throws Exception ;
	
	/**
	 * Update
	 */
	int updateEmail(long personId, char type, String newEmail) throws Exception;
	void update(Persons p) throws Exception;	
	void saveOrUpdate(Persons p) throws Exception;
	
	/**
	 * Delete
	 */
	void remove(long id) throws Exception;
}
