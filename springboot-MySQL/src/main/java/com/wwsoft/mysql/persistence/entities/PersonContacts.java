package com.wwsoft.mysql.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The idea of a owning side of a bidirectional relation comes from the fact that 
 * in relational databases there are no bidirectional relations like in the case 
 * of objects. In databases we only have unidirectional relations - foreign keys.
 * 
 * The owning side of the relation tracked by Hibernate is the side of the relation 
 * that owns the foreign key in the database.
 *
 */
@Entity
@Table(name="new_persons_contacts")
public class PersonContacts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contact_id", nullable=false)
	private long id;
	
	@Column(name="contact_type", nullable=false)
	private char type;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="create_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="update_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=false)
	private Persons person;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Persons getPerson() {
		return person;
	}

	public void setPerson(Persons person) {
		this.person = person;
	}
}
