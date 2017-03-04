package org.educama.customer.model;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.hateoas.Identifiable;

/**
 * Customer Entity
 */
@Entity
public class Customer extends AbstractPersistable<Long> implements Identifiable<Long> {

    @NotNull
    public UUID uuid;
    
    @Version
    public long version;

    @NotNull
    public String name;

    @Embedded
    @NotNull
    public Address address;

    /**
     * Constructor for JPA.
     */
    public Customer() {
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

	@Override
	public String toString() {
		return "Customer [uuid=" + uuid + ", version=" + version + ", name=" + name + ", address=" + address + "]";
	}
    
    

}
