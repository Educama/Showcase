create table shipment (id bigint not null auto_increment, customer varchar(255) not null, receiver_address varchar(255) not null, sender_address varchar(255) not null, tracking_id varchar(255), primary key (id));
alter table shipment add constraint UK_bgj4c1bm89aoqdnscu6v85rkt unique (tracking_id);
