package com.wwsoft.mysql.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "student_course",
       uniqueConstraints = { @UniqueConstraint(columnNames = {"student_id", "course_id" }) })
@SuppressWarnings("serial")
@IdClass(StudentCoursePK.class)
public class StudentCourse {
	@Id
	@Column(name="student_id", nullable = false)
	Long studentId;
	
	@Id
	@Column(name="course_id", nullable = false)
	Long courseId;
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public byte getScore() {
		return score;
	}

	public void setScore(byte score) {
		this.score = score;
	}

	@Column(name="score", nullable =true)
	byte score;
}
