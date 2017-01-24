package org.educama.customer.boundary;

import org.educama.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerBoundaryService {
	/**
	 * Creates a customer
	 *
	 * @param customer
	 *            create a new customer
	 * @return the created customer
	 */
	Customer createCustomer(Customer customer);

	/**
	 * Retrieves all customers in a pageable fashion.
	 * 
	 * @param pageable
	 *            parameter for creating pages
	 * @return a collection of all customers
	 */
	Page<Customer> findAllCustomers(Pageable pageable);

	/**
	 * Retrieves all customers using the specified suggestion in a pageable
	 * fashion.
	 * 
	 * @param name
	 *            suggestion for name
	 * @param pageable
	 *            parameter for creating pages
	 * @return a collection of all customers
	 */
	Page<Customer> findSuggestionsForCustomer(String name, Pageable pageable);

	/**
	 * Deletes the specified customer
	 * 
	 * @param customer
	 *            customer to delete
	 */
	void deleteCustomer(Customer customer);

	/**
	 * Deletes the specified customer (by id)
	 * 
	 * @param id
	 *            customer to delete
	 */
	void deleteCustomer(Long id);

	/**
	 * Updates the specified customer, if any changes are present, otherwise a
	 * EntityExistsException is thrown.
	 * 
	 * @param customer
	 *            The customer to be updated
	 * @return The updated version of the customer entity
	 */
	Customer updateCustomer(Customer customer);

	/**
	 * Retrieves the customer with the given id
	 * 
	 * @param id
	 *            id of the customer
	 * @return Customer found
	 */
	Customer findCustomerById(Long id);

}
