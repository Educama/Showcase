package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

/**
 * REST-Resource to save a customer.
 */
public class SaveCustomerResource extends ResourceSupport {

    @NotEmpty
    public String name;

    @NotNull
    public AddressDS address;

}
