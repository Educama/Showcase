package org.educama.shipment.model;

import org.educama.customer.model.Customer;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    public UUID uuidSender;

    @NotNull
    public UUID uuidReceiver;

    @NotNull
    @OneToOne
    public Customer sender;

    @NotNull
    @OneToOne
    public Customer receiver;

    @NotNull
    public boolean senderIsEmployer;

    @NotNull
    public boolean receiverIsEmployer;

    @NotNull
    public int numberPackages;

    @NotNull
    public double totalWeight;

    @NotNull
    public double totalCapacity;

    @NotNull
    public String cargoDescription;

    @NotNull
    public boolean dangerousGoods;

    @NotNull
    public boolean forerun;

    @NotNull
    public boolean exportInsurance;

    @NotNull
    public boolean exportCustomsClearance;

    @NotNull
    public boolean flight;

    @NotNull
    public boolean importInsurance;

    @NotNull
    public boolean importCustomsClearance;

    @NotNull
    public boolean backlash;
}
