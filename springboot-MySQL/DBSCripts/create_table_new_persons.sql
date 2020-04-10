-- Dont forget to add AUTO_INCREMENT to avoid "No Default value" exception in Java
-- Use timestamp to see both date and time
CREATE TABLE new_persons 
(person_id int not null AUTO_INCREMENT, 
last_name VARCHAR(20),
first_name VARCHAR(20), 
address VARCHAR(100), 
city VARCHAR(20), 
create_date timestamp, 
update_date timestamp,
primary key (person_id));

-- Misc
delete from new_persons

select * from new_persons

drop table new_persons;