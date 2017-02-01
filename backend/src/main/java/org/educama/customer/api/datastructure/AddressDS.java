/*
 * *************************************************************************
 *
 * Copyright:       Robert Bosch GmbH, 2016
 *
 * *************************************************************************
 */

/**
 * Dear "developer", please document your class RIGHT HERE!.
 */

package org.educama.customer.api.datastructure;

import org.educama.customer.model.Address;
import org.hibernate.validator.constraints.NotEmpty;

public class AddressDS {

    @NotEmpty
    public String street;

    @NotEmpty
    public String streetNo;

    @NotEmpty
    public String zipCode;

    @NotEmpty
    public String city;

    public AddressDS() {}

    public AddressDS(String street, String streetNo, String zipCode, String city) {
        this.street = street;
        this.streetNo = streetNo;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address toAddress() {
        Address address = new Address();
        address.street = this.street;
        address.streetNo = this.streetNo;
        address.zipCode = this.zipCode;
        address.city = this.city;
        return address;
    }

}
