-- Dont forget to add AUTO_INCREMENT to avoid "No Default value" exception in Java
-- Use timestamp to see both date and time
CREATE TABLE new_persons 
(person_id bigInt not null AUTO_INCREMENT, 
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

insert into new_persons
values (0, 'Wade', 'Wang', 'Test Dr', 'Test', STR_TO_DATE('2020-04-10', '%Y-%m-%d'), STR_TO_DATE('2020-04-10', '%Y-%m-%d'));