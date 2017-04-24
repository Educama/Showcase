package org.educama.customer.boundary;

import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.educama.shipment.model.Shipment;
import org.educama.shipment.repository.ShipmentRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CustomerBoundaryService}.
 */
@SuppressWarnings("checkstyle:magicnumber")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerBoundaryServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

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
        List<Shipment> shipments = shipmentRepository.findAll();
        for (Shipment shipment : shipments) {
            shipmentRepository.delete(shipment);
        }

        for (Customer customer : customers) {
            customer.address = null;
            customerRepository.delete(customer);
        }
    }

    @Test
    public void checkTestDataAvailable() {
        Page<Customer> customerPage = customerService.findAllCustomers(new PageRequest(0, 10));
        assertEquals(3, customerPage.getTotalElements());
    }

}
