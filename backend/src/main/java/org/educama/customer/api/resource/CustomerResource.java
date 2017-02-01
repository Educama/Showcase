package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.model.Customer;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

public class CustomerResource extends ResourceSupport {
	public static String NAME_DELIMITER = " ";
	
	@NotEmpty
	public String uuid;
    
	@NotEmpty
	public String name;
	
    public AddressDS address;
    
    public Customer toCustomer() {
    	Customer person = new Customer();
    	person.uuid = this.uuid;
    	person.name = NameMapper.toName(this.name);
    	if (address != null) {
    		person.address = address.toAddress();
    	}
    	
    	return person;
    }
}
