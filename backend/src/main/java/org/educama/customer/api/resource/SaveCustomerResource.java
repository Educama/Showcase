package org.educama.customer.api.resource;

import org.educama.customer.api.datastructure.AddressDS;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class SaveCustomerResource extends ResourceSupport {

    @NotEmpty
    public String name;
    
    public long version;

    @NotNull
    public AddressDS address;

	@Override
	public String toString() {
		return "SaveCustomerResource [name=" + name + ", version=" + version + ", address=" + address + "]";
	}

}
