package org.educama.shipment.model;

import org.educama.customer.model.Customer;
import org.educama.enums.ClientType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * This represents the shipment entity used for database persistence.
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "trackingId")})
public class Shipment extends AbstractPersistable<Long> {

    @Column(unique = true)
    public String trackingId;

    @NotNull
    @OneToOne
    public Customer sender;

    @NotNull
    @OneToOne
    public Customer receiver;

    @NotNull
    @Enumerated(EnumType.STRING)
    public ClientType customerTypeEnum;

    @Embedded
    @NotNull
    public Cargo shipmentCargo;

    @Embedded
    @NotNull
    public Services shipmentServices;
}
