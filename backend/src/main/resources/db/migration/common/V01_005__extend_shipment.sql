alter table shipment drop column sender_address;
alter table shipment drop column receiver_address;
alter table shipment drop column customer;

alter table shipment add column customer_type_enum varchar(255) not null;
alter table shipment add column cargo_description varchar(255);
alter table shipment add column dangerous_goods bit not null;
alter table shipment add column number_packages integer not null;
alter table shipment add column total_capacity double precision not null;
alter table shipment add column total_weight double precision not null;
alter table shipment add column export_customs_clearance bit not null;
alter table shipment add column export_insurance bit not null;
alter table shipment add column flight bit not null;
alter table shipment add column import_customs_clearance bit not null;
alter table shipment add column import_insurance bit not null;
alter table shipment add column on_carriage bit not null;
alter table shipment add column pre_carriage bit not null;
alter table shipment add column receiver_id bigint not null;
alter table shipment add column sender_id bigint not null;

alter table shipment add constraint FK4iqnob9nyy36e497kqfovf7po foreign key (receiver_id) references customer (id);
alter table shipment add constraint FK6fjq37nx2gsc584yc0eaecwwf foreign key (sender_id) references customer (id);
