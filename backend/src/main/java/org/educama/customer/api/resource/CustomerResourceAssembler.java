package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.model.Customer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

	public CustomerResourceAssembler(Class<?> controllerClass, Class<CustomerResource> resourceType) {
		super(controllerClass, resourceType);
	}

	@Override
	public CustomerResource toResource(Customer customer) {
		CustomerResource personResource = createResourceWithId(customer.getId(), customer);
		personResource.name = NameMapper.toString(customer.name);
		personResource.uuid = customer.uuid;
		
		if (customer.address != null) {
			AddressDS address = new AddressDS(customer.address.street, customer.address.streetNo, customer.address.zipCode, customer.address.city);
			personResource.address = address;
		}
		return personResource;
	}

}
