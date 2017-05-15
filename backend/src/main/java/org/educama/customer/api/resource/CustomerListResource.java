package org.educama.customer.api.resource;

import org.educama.common.api.resource.AbstractListResource;

import java.util.Collection;

/**
 * REST-Resource for a list of customers.
 */
public class CustomerListResource extends AbstractListResource {

    private final Collection<CustomerResource> customers;

    /**
     * Creates new instance of {@link CustomerListResource}.
     *
     * @param customers     collection of {@link CustomerResource company resources}
     * @param pageNumber    actual page of the collection.
     * @param pageSize      number of elements per pages of the collection.
     * @param totalPages    total number of pages of the collection.
     * @param totalElements total number of elements of the collection.
     */
    public CustomerListResource(Collection<CustomerResource> customers, int pageNumber, int pageSize, int totalPages,
                                long totalElements) {
        super(pageNumber, pageSize, totalPages, totalElements);
        this.customers = customers;
    }

    public Collection<CustomerResource> getCustomers() {
        return customers;
    }
}
