CREATE TABLE new_persons_contacts
(contact_id bigInt not null AUTO_INCREMENT, 
person_id bigInt not null,
contact_type CHAR,
email VARCHAR(20),  
create_date timestamp default now(), 
update_date timestamp default now(),
primary key (contact_id));

insert into new_persons_contacts
values(0, 1, 'P', 