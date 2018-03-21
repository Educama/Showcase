alter table shipment add column flight_id bigint;

alter table shipment add constraint foreign_key_flight_id foreign key (flight_id) references flight (id);
