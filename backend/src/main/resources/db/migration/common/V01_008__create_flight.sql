create table flight (id bigint not null auto_increment, flight_number varchar(255) not null, airline varchar(255) not null not null, airport_departure varchar(255) not null, airport_destination varchar(255) not null, price double not null, departure_time DATE not null, destination_time DATE not null, primary key (id));

alter table flight add constraint UK_aucisqx7arn3fi6eyjmsvqdb3 unique (flight_number);
