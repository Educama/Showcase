package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CustomerResource extends ResourceSupport {

    @NotNull
    public UUID uuid;
    
    @NotNull
    public long version;

    @NotNull
    @NotEmpty
    public String name;

    @NotNull
    public AddressDS address;

    /*
     * For Jackson Parser
     */
    public CustomerResource() {
        //empty
    }

    public CustomerResource(UUID uuid, long version, String name, AddressDS address) {
        this.uuid = uuid;
        this.version = version;
        this.name = name;
        this.address = address;
    }
}
