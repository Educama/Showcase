package org.educama.customer.api;

import javax.validation.Valid;

import org.educama.customer.api.resource.*;
import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-Service to access Customer resources
 */
@RestController
@RequestMapping(value = CustomerController.CUSTOMER_RESOURCE_PATH, produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomerController {

	public static final String CUSTOMER_RESOURCE_PATH = "/educama/v1/customers";

	@Autowired
	private CustomerListResourceAssembler customerListAssembler;

	@Autowired
	private CustomerBoundaryService customerService;

	/**
	 * Creates the customer if it does not exist
	 * 
	 * @param createCustomerRessource
	 * @return The newly created customer
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CustomerResource> createCustomer(
			@Valid @RequestBody CreateCustomerResource createCustomerRessource) {
		Customer customer;
		Customer newCustomer;

		if (createCustomerRessource != null) {
			customer = createCustomerRessource.toCustomer();
			newCustomer = customerService.createCustomer(customer);
			CustomerResourceAssembler assembler = new CustomerResourceAssembler(this.getClass(),
					CustomerResource.class);
			CustomerResource newCustomerResource = assembler.toResource(newCustomer);
			return new ResponseEntity<CustomerResource>(newCustomerResource, HttpStatus.CREATED);
		}
		return new ResponseEntity<CustomerResource>(HttpStatus.BAD_REQUEST);

	}

	/**
	 * Retrieves Customers in a pageable fashion
	 * 
	 * @param pageable
	 *            parameter for creating pages
	 * @return a collection of all customers
	 */
	@RequestMapping
	public CustomerListResource findAllPartner(Pageable pageable) {
		Page<Customer> page = customerService.findAllCustomers(pageable);
		CustomerListResource customerListResource = customerListAssembler.build(page);
		return customerListResource;
	}

	/**
	 * Retrieves Customers in a pageable fashion
	 * 
	 * @param pageable
	 *            parameter for creating pages
	 * @return a collection of all customers
	 */
	@RequestMapping(value = "/{id}")
	public ResponseEntity<CustomerResource> findPartnerById(@PathVariable Long id) {
		if (id != null) {
			Customer customer = customerService.findCustomerById(id);
			if (customer != null) {
				CustomerResourceAssembler assembler = new CustomerResourceAssembler(this.getClass(),
						CustomerResource.class);
				CustomerResource customerResource = assembler.toResource(customer);
				return new ResponseEntity<CustomerResource>(customerResource, HttpStatus.OK);
			}
		}
		return new ResponseEntity<CustomerResource>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Finds all customers with the suggested name
	 * 
	 * @param name
	 *            criteria for suggested names
	 * @param pageable
	 *            parameter for creating pages
	 * @return a collection of all customers having the suggested name
	 */
	@RequestMapping(value = "/suggestions")
	public CustomerListResource findSuggestionsForPartner(@RequestParam("name") String name, Pageable pageable) {
		Page<Customer> page = customerService.findSuggestionsForCustomer(name, pageable);
		CustomerListResource customerListResource = customerListAssembler.build(page);
		return customerListResource;
	}

	/**
	 * Updates the specified customer
	 * 
	 * @param customerRessource
	 *            customer to update
	 * @return HttpStatus.OK if updated, otherwise HttpStatus.BAD_REQUEST
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CustomerResource> updateCustomer(@PathVariable Long id,
			@Valid @RequestBody CustomerResource customerRessource) {
		if (customerRessource != null) {
			Customer customer = customerRessource.toCustomer();
			customer.setId(id);
			Customer updatedCustomer = customerService.updateCustomer(customer);

			CustomerResourceAssembler assembler = new CustomerResourceAssembler(CustomerController.class,
					CustomerResource.class);
			CustomerResource updatedCustomerResource = assembler.toResource(updatedCustomer);
			return new ResponseEntity<CustomerResource>(updatedCustomerResource, HttpStatus.OK);
		}
		return new ResponseEntity<CustomerResource>(HttpStatus.BAD_REQUEST);

	}

	/**
	 * Deletes the specified customer (by id)
	 * 
	 * @param id
	 *            customer to delete
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePartner(@PathVariable Long id) {
		customerService.deleteCustomer(id);
	}
}