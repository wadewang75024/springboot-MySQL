package com.wwsoft.mysql.persistence.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="new_persons")
/**
 * The following queries are used by Hibernate:
 * 			- PersonContact_findByPersonId
 * 			- findAllPersonsWithContacts_native
 * 
 * The following queries are used by JPA:
 * 			******** note that the prefix Persons.******************* :
 * 			- Persons.findPersonsWithContacts
 */
@NamedQuery(name = "PersonContact_findByPersonId", 
            query = "from Persons p left join fetch p.personContacts where p.id=:personId")
@NamedNativeQuery(name="findAllPersonsWithContacts_native", 
				  query="select p.person_id, p.last_name, p.first_name, pc.contact_id, pc.email " + 
                        " from new_persons p, new_persons_contacts pc where p.person_id=pc.contact_id " )
@NamedQuery(name="Persons.findPersonsWithContacts", 
//			query="select distinct p from Persons p where p.id=:personId")
            query="select distinct p from Persons p left join fetch p.personContacts where p.id=:personId")
public class Persons {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="person_id", nullable = false)
	private Long id;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="address")
	private String address;
	
	// **********************************************************************************
	// * Add @Temporal(TemporalType.TIMESTAMP) to be able to save 
	// * both date and time to the database
	// * ******************************************************************************
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")	
	private Date createDate ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate ;
	
	@Column(name="city")
	private String city ;
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<PersonContacts> personContacts;
	
	public List<PersonContacts> getPersonContacts() {
		return personContacts;
	}

	public void setPersonContacts(List<PersonContacts> personContacts) {
		this.personContacts = personContacts;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
