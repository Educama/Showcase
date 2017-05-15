package org.educama.customer.model;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * This represents the customer entity used for database persistence.
 */
@SuppressWarnings("serial")
@Entity
public class Customer extends AbstractPersistable<Long> implements Identifiable<Long> {

    @NotNull
    public UUID uuid;

    @NotNull
    public String name;

    @Embedded
    @NotNull
    public Address address;

    /**
     * Constructor for JPA.
     */
    protected Customer() {
        //empty
    }

    public Customer(String name, Address address) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.address = address;
    }

    public Customer(UUID uuid, String name, Address address) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
    }

}
