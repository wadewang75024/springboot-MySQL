package com.wwsoft.mysql.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

@Entity
@Table(name="students")
public class Students {
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
		name = "student_course", 
		joinColumns = { @JoinColumn(name = "student_id", nullable = false, updatable = false) },
		inverseJoinColumns = { @JoinColumn(name = "course_id", nullable = false, updatable = false) })
	private Set<Courses> courses = new HashSet<Courses>(0);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="student_id", nullable=false)
	Long id;
	
	@Column(name="last_name")
	String lastName;
	
	@Column(name="first_name")
	String firstName;
	
	@Column(name="email")
	String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date", nullable=false)
	Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date", nullable=false)
	Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Set<Courses> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}
}
