package org.educama.customer.boundary.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.boundary.EntityExistsException;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.AddressRepository;
import org.educama.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerBoundaryServiceImpl implements CustomerBoundaryService {

	private static final Pageable DEFAULT_PAGEABLE = new PageRequest(0, 10);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Customer createCustomer(Customer customer) {
		notNull(customer);
		Customer newCustomer = null;

		if (customer.address != null) {
			Address updatedAddress = this.saveIfChangedOrNotExists(customer.address);
			customer.address = updatedAddress;
		}
		customer.generateUuid();
		newCustomer = customerRepository.save(customer);
		return newCustomer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		notNull(customer);
		Customer updatedCustomer = null;
		Address updatedAddress = null;

		Customer oldCustomer = customerRepository.findByUuid(customer.uuid);

		if (oldCustomer.equalsByValue(customer)) {
			throw new EntityExistsException("Entity has not been updated, because no changes were detected",
					customer.toString());
		}
		
		if (customer.address != null) {
			updatedAddress = this.saveIfChangedOrNotExists(customer.address);
			customer.address = updatedAddress;
		}
		updatedCustomer = customerRepository.save(customer);

		return updatedCustomer;
	}

	@Override
	public Page<Customer> findAllCustomers(Pageable pageable) {
		if (pageable == null) {
			pageable = DEFAULT_PAGEABLE;
		}
		return customerRepository.findAll(pageable);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		notNull(customer);
		customerRepository.delete(customer);

	}

	@Override
	public void deleteCustomer(Long id) {
		notNull(id);
		customerRepository.delete(id);

	}

	private Address saveIfChangedOrNotExists(Address address) {
		Address returnAddress = null;
		Example<Address> example = Example.of(address);
		List<Address> existingAddress = addressRepository.findAll(example);
		if (existingAddress.size() == 0) {
			returnAddress = addressRepository.save(address);
		} else {
			returnAddress = existingAddress.get(0);
		}
		return returnAddress;
	}

	@Override
	public Page<Customer> findSuggestionsForCustomer(String name, Pageable pageable) {
		notNull(name);
		notNull(pageable);
		Page<Customer> page = customerRepository.findSuggestionByName(name, pageable);
		return page;
	}

	@Override
	public Customer findCustomerById(Long id) {
		notNull(id);
		Customer customer = customerRepository.findOne(id);
		return customer;
	}

}
