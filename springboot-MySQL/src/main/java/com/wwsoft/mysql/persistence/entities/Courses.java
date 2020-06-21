package com.wwsoft.mysql.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="courses")
public class Courses {
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	private Set<Students> students = new HashSet<Students>(0);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="course_id", nullable=false)
	Long id;
	
	@Column(name="course_name", nullable=false)
	String courseName;
	
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	
	public Set<Students> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Students> students) {
		this.students = students;
	}
}
