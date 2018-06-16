package org.educama.shipment.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.educama.customer.model.Customer;
import org.educama.enums.ClientType;
import org.educama.enums.Status;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * This represents the shipment entity used for database persistence.
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "trackingId") })
public class Shipment extends AbstractPersistable<Long> {

    @NotNull
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

    @Embedded
    public Flight shipmentFlight;

    @Enumerated(EnumType.STRING)
    public Status statusEnum;
}
