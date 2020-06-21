-- Dont forget to add AUTO_INCREMENT to avoid "No Default value" exception in Java
-- Use timestamp to see both date and time
CREATE TABLE students 
(student_id bigInt not null AUTO_INCREMENT, 
last_name VARCHAR(20),
first_name VARCHAR(20), 
email VARCHAR(100), 
create_date timestamp default now(), 
update_date timestamp default now(),
primary key (student_id));

CREATE TABLE courses 
(course_id bigInt not null AUTO_INCREMENT, 
course_name VARCHAR(20),
create_date timestamp default now(), 
update_date timestamp default now(),
primary key (course_id));

CREATE TABLE student_course
(student_id bigInt not null,
course_id bigInt not null,
score tinyInt null, 
create_date timestamp default now(), 
update_date timestamp default now(),
primary key (student_id, course_id),
constraint student_course_ibfk_1 foreign key (student_id) references students(student_id),
constraint student_course_ibfk_2 foreign key (course_id) references courses(course_id)
);

select * from students

select * from courses

select * from student_course

-- drop table students;
-- drop table courses;
-- drop table student_course;

-- Misc
-- delete from students
where person_id between 111 and 116

-- Test data
insert into students(student_id, last_name, first_name, email)
values (0, 'Wang', 'Wade', 'wwang@gmail.com');

insert into students(student_id, last_name, first_name, email)
values (0, 'Yu', 'Mary', 'tmary@gmail.com');
