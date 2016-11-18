package org.educama.shipment.model;

import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;

@Entity
public class Shipment extends AbstractPersistable<Long> {

    public String senderAddress;
    public String receiverAddress;
}
