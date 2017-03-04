package org.educama.customer.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;

/**
 * Address entity
 */
@Embeddable
public class Address {

    @NotEmpty
    public String street;

    @NotEmpty
    public String streetNo;

    @NotEmpty
    public String zipCode;

    @NotEmpty
    public String city;

    /**
     * Constructor for JPA.
     */
    public Address() {
        //empty
    }

    public Address(String street, String streetNo, String zipCode, String city) {
        this.street = street;
        this.streetNo = streetNo;
        this.zipCode = zipCode;
        this.city = city;
    }

	@Override
	public String toString() {
		return "Address [street=" + street + ", streetNo=" + streetNo + ", zipCode=" + zipCode + ", city=" + city + "]";
	}
}
