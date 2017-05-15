package org.educama.customer.api.resource.assembler;

import org.educama.common.api.resource.PageLinks;
import org.educama.customer.api.CustomerController;
import org.educama.customer.api.resource.CustomerListResource;
import org.educama.customer.api.resource.CustomerResource;
import org.educama.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Assembler to build {@link CustomerListResource}.
 */
@Component
public class CustomerListResourceAssembler {

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @PageLinks(CustomerController.class)
    public CustomerListResource build(Page<Customer> page) {

        List<CustomerResource> customerList = page.getContent()
                .stream()
                .map(customer ->  customerResourceAssembler.toResource(customer))
                .collect(Collectors.toList());

        return new CustomerListResource(customerList, page.getNumber(),
                page.getSize(), page.getTotalPages(), page.getTotalElements());
    }

}
