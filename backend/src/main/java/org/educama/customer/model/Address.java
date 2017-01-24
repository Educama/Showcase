package org.educama.customer.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.hateoas.Identifiable;

/**
 * AddressDS entity
 * @author user
 */
@Entity
public class Address extends AbstractPersistable<Long> implements Identifiable<Long>  {

	private static final long serialVersionUID = -6503692835232524166L;
	
	@NotEmpty
    public String street;
	
	@NotEmpty
    public String streetNo;
	
	@NotEmpty
    public String zipCode;
	
	@NotEmpty
    public String city;
    
    /**
     * Constructor to be able to set id of entity explicitly
     * @param id Primary key of address
     */
    public Address(Long id) {
    	setId(id);
    }
    
    public Address() {
    }
    
    public void setId(Long id) {
    	super.setId(id);
    }

	public Address(String street, String streetNo, String zipCode, String city) {
		this();
		this.street = street;
		this.streetNo = streetNo;
		this.zipCode = zipCode;
		this.city = city;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((streetNo == null) ? 0 : streetNo.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (streetNo == null) {
			if (other.streetNo != null)
				return false;
		} else if (!streetNo.equals(other.streetNo))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	public boolean equalsByValue(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (streetNo == null) {
			if (other.streetNo != null)
				return false;
		} else if (!streetNo.equals(other.streetNo))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
}
