package org.educama.api;

import org.educama.customer.api.resource.CustomerListResource;
import org.educama.customer.api.resource.CustomerListResourceAssembler;
import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.customer.repository.AddressRepository;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerServiceTest {

	@Autowired
	private CustomerRepository personRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CustomerBoundaryService partnerService;

	@Autowired
	private CustomerListResourceAssembler assembler;

	@Before
	public void createTestData() {
		this.deleteTestData();
		Address address = new Address("Königstraße", "38", "70013", "Stuttgart");
		Address savedAddress = addressRepository.save(address);

		Customer person = new Customer("Marty", "Maredo");
		person.address = savedAddress;
		personRepository.save(person);

		person = new Customer("Marge", "Maredo");
		person.address = savedAddress;
		personRepository.save(person);

		person = new Customer("Mat", "Maredo");
		person.address = savedAddress;
		personRepository.save(person);
	}

	@After
	public void deleteTestData() {
		List<Customer> allPersons = personRepository.findAll();
		for (Customer person : allPersons) {
			person.address = null;
			personRepository.delete(person);
		}

		List<Address> allAddresses = addressRepository.findAll();
		for (Address address : allAddresses) {
			addressRepository.delete(address);
		}
	}

	@Test
	public void createAddressResource() {
		PageRequest pageRequest = new PageRequest(0, 1);
		Page<Customer> page = partnerService.findAllPartner(pageRequest);
		CustomerListResource customerListResource = assembler.build(page);
		assertTrue(customerListResource != null);
		assertEquals(1, customerListResource.getCustomers().size());
	}

}
