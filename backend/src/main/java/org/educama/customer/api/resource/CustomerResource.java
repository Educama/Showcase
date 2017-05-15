package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * REST-Resource for single customer.
 */
public class CustomerResource extends ResourceSupport {

    @NotNull
    public UUID uuid;

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    public AddressDS address;

    /*
     * For Jackson Parser
     */
    private CustomerResource() {
        //empty
    }

    public CustomerResource(UUID uuid, String name, AddressDS address) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
    }
}
