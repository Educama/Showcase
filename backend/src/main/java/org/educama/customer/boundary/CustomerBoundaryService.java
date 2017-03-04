package org.educama.customer.boundary;

import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerBoundaryService {
    /**
     * Creates a customer
     *
     * @param name  The name of the customer to be create
     * @param address  The address {@AddressDS} of the customer to be created
     * @return the created customer
     */
    Customer createCustomer(String name, AddressDS address);

    /**
     * Updates the specified customer, if any changes are present, otherwise a
     * EntityExistsException is thrown.
     *
     * @param uuid  The uuid of the customer to be updated
     * @param version The version of the customer for optimistic locking
     * @param name  The name of the customer to be updated
     * @param address  The address {@AddressDS} of the customer to be updated
     * @return The updated version of the customer entity
     */
    Customer updateCustomer(UUID uuid, long version, String name, AddressDS address);

    /**
     * Deletes the specified customer (by uuid)
     *
     * @param uuid uuid of the customer to delete
     */
    void deleteCustomer(UUID uuid);

    /**
     * Retrieves all customers in a pageable fashion.
     *
     * @param pageable parameter for creating pages
     * @return a collection of all customers
     */
    Page<Customer> findAllCustomers(Pageable pageable);

    /**
     * Retrieves all customers using the specified suggestion in a pageable
     * fashion.
     *
     * @param name     suggestion for name
     * @param pageable parameter for creating pages
     * @return a collection of all customers
     */
    Page<Customer> findSuggestionsForCustomer(String name, Pageable pageable);

    /**
     * Retrieves the customer with the given uuid
     *
     * @param uuid uuid of the customer
     * @return Customer found
     */
    Customer findCustomerByUuid(UUID uuid);

}
