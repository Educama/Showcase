package org.educama.customer.repository;

import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerRepositoryTest {

	private static final Pageable PAGEABLE = new PageRequest(0, 10);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Before
	public void createTestData() {
		this.deleteTestData();
		Address address = new Address("Königstraße", "38", "70013", "Stuttgart");
		Address savedAddress = addressRepository.save(address);

		Customer customer = new Customer("Marty", "Maredo");
		customer.address = savedAddress;
		customerRepository.save(customer);

		customer = new Customer("Marge", "Maredo");
		customer.address = savedAddress;
		customerRepository.save(customer);

		customer = new Customer("Mat", "Maredo");
		customer.address = savedAddress;
		customerRepository.save(customer);
	}

	@After
	public void deleteTestData() {
		List<Customer> allCustomers = customerRepository.findAll();
		for (Customer customer : allCustomers) {
			customer.address = null;
			customerRepository.delete(customer);
		}

		List<Address> allAddresses = addressRepository.findAll();
		for (Address address : allAddresses) {
			addressRepository.delete(address);
		}
	}

	@Test
	public void createCustomerIncomplete() {
		Customer savedCustomer = null;
		try {
			Customer customer = new Customer("Marty", null);
			savedCustomer = customerRepository.save(customer);
			fail();
		} catch (DataIntegrityViolationException e) {
			assertTrue(savedCustomer == null);
		}

	}

	@Test
	public void checkTestDataAvailable() {
		List<Customer> allCustomers = customerRepository.findAll();
		assertEquals(3, allCustomers.size());
		List<Address> allAddresses = addressRepository.findAll();
		assertEquals(1, allAddresses.size());
	}

	@Test
	public void deleteAddressForCustomer() {
		Page<Customer> customers = customerRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer customer = customers.getContent().get(0);
		assertEquals("Marty", customer.name.firstname);
		assertEquals("Maredo", customer.name.lastname);
		assertEquals("70013", customer.address.zipCode);
		assertEquals("Stuttgart", customer.address.city);
		customer.address = null;
		Customer savedCustomer = customerRepository.save(customer);
		assertEquals(null, savedCustomer.address);
		List<Address> allAddresses = addressRepository.findAll();
		assertEquals(1, allAddresses.size());
	}

	@Test
	public void deleteAddressUnsafe() {
		try {
			List<Address> allAddresses = addressRepository.findAll();
			for (Address address : allAddresses) {
				addressRepository.delete(address);
				fail();
			}
		} catch (DataIntegrityViolationException e) {
			assertTrue(e != null);
		}
	}

	@Test
	public void deleteAddressSafe() {
		List<Customer> allCustomers = customerRepository.findAll();
		for (Customer customer : allCustomers) {
			customer.address = null;
			customerRepository.save(customer);
		}
		List<Address> allAddresses = addressRepository.findAll();
		for (Address address : allAddresses) {
			addressRepository.delete(address);
		}
		assertEquals(3, customerRepository.count());
		assertEquals(0, addressRepository.count());
	}

	@Test
	public void deleteCustomer() {
		Page<Customer> customers = customerRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer customer = customers.getContent().get(0);
		customerRepository.delete(customer);

		assertEquals(2, customerRepository.count());
		assertEquals(1, addressRepository.count());
	}

	@Test
	public void pageThroughCustomers() {
		String[] firstNames = new String[]{ "Marty", "Marge", "Mat" };
		int loopCount = 0;
		Pageable pageable = new PageRequest(0 /* first */, 2 /* one at a time */);
		Page<Customer> page = null;

		do {
			page = customerRepository.findAll(pageable);
			loopCount++;
			assertEquals(3, page.getTotalElements());
			if (loopCount != 2) {
				assertEquals(2, page.getNumberOfElements());
			} else {
				assertEquals(1, page.getNumberOfElements());
			}

			Customer customer = page.getContent().get(0);
			assertEquals(firstNames[pageable.getPageNumber()*2], customer.name.firstname);
			pageable = pageable.next();
		} while (pageable.getPageNumber()*pageable.getPageSize() < page.getTotalElements());
		assertEquals(2,  loopCount);
	}

	@Test
	public void createSameAddressTwice() {
		Address address = new Address("Königstraße", "38", "70013", "Stuttgart");
		Example<Address> example = Example.of(address);
		List<Address> existingAddress = addressRepository.findAll(example);
		assertEquals(1, existingAddress.size());
	}

	@Test
	public void updateAddress() {
		Address address = new Address("Königstraße", "39", "70013", "Stuttgart");
		Example<Address> example = Example.of(address);
		List<Address> existingAddress = addressRepository.findAll(example);
		assertEquals(0, existingAddress.size());
	}


	@Test
	public void emptyPageable() {
		Page<Customer> page = customerRepository.findAll((Pageable)null);
		assertEquals(3, page.getTotalElements());
		assertEquals(3, page.getContent().size());
	}

	@Test
	public void updateCustomersAddressWrongWay() {
		Page<Customer> customers = customerRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer customer = customers.getContent().get(0);
		customer.name.lastname = "Montana";
		customer.address.city = "Vaihingen";
		Customer savedCustomer = customerRepository.save(customer);
		assertEquals("Marty", savedCustomer.name.firstname);
		assertEquals("Montana", savedCustomer.name.lastname);
		assertTrue(!savedCustomer.address.city.equals("Vaihingen"));
	}

	@Test
	public void updateCustomersAddressCorrect() {
		Page<Customer> customers = customerRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer customer = customers.getContent().get(0);
		customer.name.lastname = "Montana";
		customer.address.city = "Vaihingen";
		Address savedAddress = addressRepository.save(customer.address);

		Customer savedCustomer = customerRepository.save(customer);
		assertEquals("Marty", savedCustomer.name.firstname);
		assertEquals("Montana", savedCustomer.name.lastname);
		assertEquals("Vaihingen", savedCustomer.address.city);
		assertEquals(savedAddress, savedCustomer.address);
	}

	@Test
	public void updateCustomer() {
		Page<Customer> customers = customerRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer customer = customers.getContent().get(0);
		customer.name.lastname = "Montana";
		Customer savedCustomer = customerRepository.save(customer);
		assertEquals("Marty", savedCustomer.name.firstname);
		assertEquals("Montana", savedCustomer.name.lastname);
	}

}
