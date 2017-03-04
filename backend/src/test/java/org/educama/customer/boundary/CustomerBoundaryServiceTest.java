package org.educama.customer.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.educama.customer.api.datastructure.AddressDS;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerBoundaryServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerBoundaryService customerService;

    @Before
    public void createTestData() {
        this.deleteTestData();
        Address address = new Address("Königstraße", "38", "70013", "Stuttgart");

        Customer person = new Customer("Marty Maredo", address);
        customerRepository.save(person);

        person = new Customer("Marge Maredo", address);
        customerRepository.save(person);

        person = new Customer("Mat Maredo", address);
        customerRepository.save(person);
    }

    @After
    public void deleteTestData() {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customer.address = null;
            customerRepository.delete(customer);
        }
    }

    @Test
    public void checkTestDataAvailable() {
        Page<Customer> customerPage = customerService.findAllCustomers(new PageRequest(0, 10));
        assertThat(customerPage.getTotalElements()).isEqualTo(3);
    }

    @Test
    public void updatingCustomerWithSameVersionTwiceGiveResultsInOptimisticLockFailure() {
        Customer customer = customerService.findAllCustomers(null).getContent().get(0);
        customerService.updateCustomer(customer.uuid, customer.version, "Should persist",
                new AddressDS(customer.address));
        assertThatExceptionOfType(OptimisticLockingFailureException.class).isThrownBy(()-> customerService.updateCustomer(customer.uuid,
                customer.version, "Second update", new AddressDS(customer.address))).withNoCause().withMessageContaining("indicates an optimistic lock failure");
        Customer updatedCustomerFromDb = customerService.findCustomerByUuid(customer.uuid);
        assertThat(updatedCustomerFromDb.name).isEqualTo("Should persist");
    }
    
    @Test
    public void updatingCustomerWithIncrementedVersionSuccessfullyUpdatesTwice() {
        Customer customer = customerService.findAllCustomers(null).getContent().get(0);
        customerService.updateCustomer(customer.uuid, customer.version, "Should not persist",
                new AddressDS(customer.address));
        Customer updatedCustomerFromDb = customerService.findCustomerByUuid(customer.uuid);
        assertThat(updatedCustomerFromDb.name).isEqualTo("Should not persist");
        customerService.updateCustomer(updatedCustomerFromDb.uuid, updatedCustomerFromDb.version, "Should persist",
                new AddressDS(updatedCustomerFromDb.address));
        updatedCustomerFromDb = customerService.findCustomerByUuid(customer.uuid);
        assertThat(updatedCustomerFromDb.name).isEqualTo("Should persist");
    }

    // TODO: To be extended

}
