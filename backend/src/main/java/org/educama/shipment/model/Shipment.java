package org.educama.shipment.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class Shipment extends AbstractPersistable<Long> {

    public String senderAdress;
    public String receiverAdress;
}
