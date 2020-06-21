package com.wwsoft.mysql.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentCoursePK implements Serializable {

		private static final long serialVersionUID = 1625128961182625883L;
		
		@Column(name="student_id", nullable=false)
		private Long studentId;
		
		@Column(name="course_id", nullable=false)
		private Long courseId;
			
		public StudentCoursePK() {}

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
}
