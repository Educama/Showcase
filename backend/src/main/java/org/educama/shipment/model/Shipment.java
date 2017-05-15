package org.educama.shipment.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * This represents the shipment entity used for database persistence.
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "trackingId")})
public class Shipment extends AbstractPersistable<Long> {

    @Column(unique = true)
    public String trackingId;

    @NotBlank
    public String customer;

    @NotBlank
    public String senderAddress;

    @NotBlank
    public String receiverAddress;
}
