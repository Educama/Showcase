package org.educama.customer.boundary.impl;

import static org.springframework.util.Assert.notNull;

import java.util.UUID;
import java.util.logging.Logger;

import org.educama.common.exceptions.ResourceNotFoundException;
import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerBoundaryServiceImpl implements CustomerBoundaryService {
	
	private static final Logger logger = Logger.getLogger(CustomerBoundaryServiceImpl.class.getCanonicalName());

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
    public Customer updateCustomer(UUID uuid, long version, String name, AddressDS address) {
        notNull(uuid);
        notNull(name);
        notNull(address);

        Customer customer = customerRepository.findByUuid(uuid);
        logger.fine(()-> "customer from database: " + customer);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }else if(customer.version != version) {
            // Setting the version manually has not the desired effect of having an OptimisticLockFailureException thrown in case of concurrent editing.
            // Therefore this check is done manually.
            logger.warning("Versions of the saved customer (" + customer.version +") and the updated one (" + version + ") do not match. This indicates an optimistic lock failure.");
            throw new OptimisticLockingFailureException("Versions of the saved customer (" + customer.version +") and the updated one (" + version + ") do not match. This indicates an optimistic lock failure.");
        }
        else {
            customer.name = name;
            customer.address = address.toAddress();
            logger.fine(()-> "customer before save: " + customer);
            Customer savedCustomer = customerRepository.save(customer);
            return savedCustomer;
        }
    }

    @Override
    public void deleteCustomer(UUID uuid) {
        notNull(uuid);
        Customer customer = customerRepository.findByUuid(uuid);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        else {
            customerRepository.delete(customer.getId());
        }
    }

    @Override
    public Customer findCustomerByUuid(UUID uuid) {
        notNull(uuid);
        Customer customer = customerRepository.findByUuid(uuid);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        else {
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
