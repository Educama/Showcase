package org.educama.shipment.api.datastructure;

import java.util.UUID;

import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.model.Customer;

/**
 * Re-usable data structure used by resources.
 */
public class PartyDS {

    public String name;

    public UUID uuid;

    public AddressDS address;

    public PartyDS(Customer customer) {

        this.uuid = customer.uuid;
        this.name = customer.name;
        this.address = new AddressDS(customer.address);
    }

}
