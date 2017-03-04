package org.educama.customer.api.resource.assembler;

import org.educama.customer.api.CustomerController;
import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.api.resource.CustomerResource;
import org.educama.customer.model.Customer;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler {

    public CustomerResource toResource(Customer customer) {
        CustomerResource resource = new CustomerResource(customer.uuid,customer.version, customer.name, new AddressDS(customer.address));

        // Add self reference
        resource.add(linkTo(methodOn(CustomerController.class).findOneByUuid(customer.uuid)).withSelfRel());

        return resource;
    }

}
