package org.educama.customer.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Customer Entity
 */
@Entity
public class Customer extends AbstractPersistable<Long> implements Identifiable<Long> {

	private static final long serialVersionUID = -7728962437347271840L;

	
	/**
     * Constructor to be able to set id of entity explicitly
     * @param id Primary key of address
     */
	public Customer(Long id) {
		this();
		setId(id);
	}
	
	public Customer() {
	}
	
	public void generateUuid() {
    	this.uuid = UUID.randomUUID().toString();
    }

	public Customer(String firstname, String lastname) {
		this();
		this.name = new Name(firstname, lastname);
	}
	
	public Customer(String uuid, String firstname, String lastname) {
		this(firstname, lastname);
		this.uuid = uuid;
	}
	
	public void setId(Long id) {
    	super.setId(id);
    }
	
	@NotEmpty
    public String uuid;
	
	@Embedded
	@NotNull
	public Name name;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idAddress", referencedColumnName = "id")
	public Address address;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	public boolean equalsByValue(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equalsByValue(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
