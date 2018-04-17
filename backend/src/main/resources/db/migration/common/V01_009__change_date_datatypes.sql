ALTER TABLE shipment DROP COLUMN departure_time;
ALTER TABLE shipment DROP COLUMN destination_time;

ALTER TABLE shipment ADD COLUMN departure_time TIMESTAMP null;
ALTER TABLE shipment ADD COLUMN destination_time TIMESTAMP null;
