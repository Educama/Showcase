package org.educama.customer.boundary.impl;

import org.educama.common.exceptions.ResourceNotFoundException;
import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.util.Assert.notNull;

/**
 * Boundary service implementation for customer.
 */
@Service
public class CustomerBoundaryServiceImpl implements CustomerBoundaryService {

    private static final Pageable DEFAULT_PAGEABLE = new PageRequest(0, 10);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String name, AddressDS address) {
        notNull(name);
        notNull(address);

        Customer newCustomer = new Customer(name, address.toAddress());

        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(UUID uuid, String name, AddressDS address) {
        notNull(uuid);
        notNull(name);
        notNull(address);

        Customer customer = customerRepository.findByUuid(uuid);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        } else {
            customer.name = name;
            customer.address = address.toAddress();
            return customerRepository.save(customer);
        }
    }

    @Override
    public void deleteCustomer(UUID uuid) {
        notNull(uuid);
        Customer customer = customerRepository.findByUuid(uuid);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        } else {
            customerRepository.delete(customer.getId());
        }
    }

    @Override
    public Customer findCustomerByUuid(UUID uuid) {
        notNull(uuid);
        Customer customer = customerRepository.findByUuid(uuid);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        } else {
            return customer;
        }
    }

    @Override
    public Page<Customer> findAllCustomers(Pageable pageable) {
        if (pageable == null) {
            pageable = DEFAULT_PAGEABLE;
        }
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> findSuggestionsForCustomer(String name, Pageable pageable) {
        notNull(name);
        notNull(pageable);
        Page<Customer> page = customerRepository.findSuggestionByName(name, pageable);
        return page;
    }

}
