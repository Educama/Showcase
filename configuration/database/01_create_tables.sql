USE educama;

CREATE TABLE Address (
  id int(11) NOT NULL AUTO_INCREMENT,
  uuid varchar(256) NOT NULL,
  street varchar(80) NOT NULL,
  streetNo varchar(40) NOT NULL,
  zipCode varchar(40) NOT NULL,
  city varchar(80) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE Person (
  id int(11) NOT NULL AUTO_INCREMENT,
  uuid varchar(256) NOT NULL,
  firstname varchar(80) NOT NULL,
  lastname varchar(80) NOT NULL,
  idAddress int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (idAddress) REFERENCES Address (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE UNIQUE INDEX IDX_ADDRESS_UUID ON Address(uuid);

CREATE TABLE Shipment (
  id int(11) NOT NULL AUTO_INCREMENT,
  trackingId varchar(256) NOT NULL,
  idSender int(11) NOT NULL,
  idReceiver int(11) NOT NULL,
  clientType varchar(10) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idSender) REFERENCES Person (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  FOREIGN KEY (idReceiver) REFERENCES Person (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX IDX_PERSON_UUID ON Person(uuid);

