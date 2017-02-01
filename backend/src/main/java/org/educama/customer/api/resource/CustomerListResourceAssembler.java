package org.educama.customer.api.resource;

import org.educama.common.api.resource.PageLinks;
import org.educama.customer.api.CustomerController;
import org.educama.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerListResourceAssembler {

    @PageLinks(CustomerController.class)
    public CustomerListResource build(Page<Customer> page) {
        List<CustomerResource> personList = page.getContent().stream().map((person) -> {
            CustomerResourceAssembler assembler = new CustomerResourceAssembler(CustomerController.class, CustomerResource.class);
            CustomerResource resource = assembler.toResource(person);
            return resource;
        }).collect(Collectors.toList());
        CustomerListResource listResource = new CustomerListResource(personList, page.getNumber(),
                page.getSize(), page.getTotalPages(), page.getTotalElements());
        return listResource;
    }

}
