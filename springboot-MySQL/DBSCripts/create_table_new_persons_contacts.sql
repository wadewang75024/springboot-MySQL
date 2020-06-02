CREATE TABLE new_persons_contacts
(contact_id bigInt not null AUTO_INCREMENT, 
person_id bigInt not null,
contact_type CHAR,
email VARCHAR(20),  
create_date timestamp default now(), 
update_date timestamp default now(),
primary key (contact_id));

select * from new_persons
where last_name="Test"

select * from new_persons p, new_persons_contacts pc
where p.last_name="Test" 
and p.person_id=pc.person_id

select * from new_persons_contacts
where person_id in (3,30,31,35,38,39,40,41,42,43)

select p.person_id, p.last_name, p.first_name, pc.contact_id, pc.email
from new_persons p, new_persons_contacts pc where p.person_id=pc.contact_id
// drop table new_persons_contacts;
// update new_persons_contacts set email='dummy1@gmail.com' where contact_id=2

insert into new_persons_contacts 
(contact_id, person_id, contact_type, email)
values(0, 3, 'P', 'dummy@gmail.com');

insert into new_persons_contacts 
(contact_id, person_id, contact_type, email)
values(0, 30, 'T', 'dummy@gmail.com');

insert into new_persons_contacts 
(contact_id, person_id, contact_type, email)
values(0, 31, 'P', 'dummy@gmail.com');

insert into new_persons_contacts 
(contact_id, person_id, contact_type, email)
values(31, 1, 'T', 'dummy@gmail.com');

// SET FOREIGN_KEY_CHECKS=OFF;