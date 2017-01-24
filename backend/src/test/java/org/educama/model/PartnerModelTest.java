package org.educama.model;

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
public class PartnerModelTest {
	
	private static final Pageable PAGEABLE = new PageRequest(0, 10);

	@Autowired
	private CustomerRepository personRepository;

	@Autowired
	private AddressRepository addressRepository;

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
	public void createPersonIncomplete() {
		Customer savedPerson = null;
		try {
			Customer person = new Customer("Marty", null);
			savedPerson = personRepository.save(person);
			fail();
		} catch (DataIntegrityViolationException e) {
			assertTrue(savedPerson == null);
		}

	}

	@Test
	public void checkTestDataAvailable() {
		List<Customer> allPersons = personRepository.findAll();
		assertEquals(3, allPersons.size());
		List<Address> allAddresses = addressRepository.findAll();
		assertEquals(1, allAddresses.size());
	}

	@Test
	public void deleteAddressForPerson() {
		Page<Customer> persons = personRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer person = persons.getContent().get(0);
		assertEquals("Marty", person.name.firstname);
		assertEquals("Maredo", person.name.lastname);
		assertEquals("70013", person.address.zipCode);
		assertEquals("Stuttgart", person.address.city);
		person.address = null;
		Customer savedPerson = personRepository.save(person);
		assertEquals(null, savedPerson.address);
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
		List<Customer> allPersons = personRepository.findAll();
		for (Customer person : allPersons) {
			person.address = null;
			personRepository.save(person);
		}
		List<Address> allAddresses = addressRepository.findAll();
		for (Address address : allAddresses) {
			addressRepository.delete(address);
		}
		assertEquals(3, personRepository.count());
		assertEquals(0, addressRepository.count());
	}
	
	@Test
	public void deletePerson() {
		Page<Customer> persons = personRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer person = persons.getContent().get(0);
		personRepository.delete(person);
		
		assertEquals(2, personRepository.count());
		assertEquals(1, addressRepository.count());
	}
	
	@Test 
	public void pageThroughPersons() {
		String[] firstNames = new String[]{ "Marty", "Marge", "Mat" };
		int loopCount = 0;
		Pageable pageable = new PageRequest(0 /* first */, 2 /* one at a time */);
		Page<Customer> page = null;
		do {
			page = personRepository.findAll(pageable);
			loopCount++;
			assertEquals(3, page.getTotalElements());
			if (loopCount != 2) {
				assertEquals(2, page.getNumberOfElements());
			} else {
				assertEquals(1, page.getNumberOfElements());
			}
			Customer person = page.getContent().get(0);
			assertEquals(firstNames[pageable.getPageNumber()*2], person.name.firstname);
			pageable = pageable.next();
		} while (pageable.getPageNumber()*pageable.getPageSize() < page.getTotalElements());
		assertEquals(2,  loopCount);
	}
	
	@Test
	public void createSameAddressTwice() {
		Address address = new Address(null, "Königstraße", "38", "70013", "Stuttgart");
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
		Page<Customer> page = personRepository.findAll((Pageable)null);
		assertEquals(3, page.getTotalElements());
		assertEquals(3, page.getContent().size());
	}
	
	@Test
	public void updatePersonsAddressWrongWay() {
		Page<Customer> persons = personRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer person = persons.getContent().get(0);
		person.name.lastname = "Montana";
		person.address.city = "Vaihingen";
		Customer savedPerson = personRepository.save(person);
		assertEquals("Marty", savedPerson.name.firstname);
		assertEquals("Montana", savedPerson.name.lastname);
		assertTrue(!savedPerson.address.city.equals("Vaihingen"));
	}
	
	@Test
	public void updatePersonsAddressCorrect() {
		Page<Customer> persons = personRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer person = persons.getContent().get(0);
		person.name.lastname = "Montana";
		person.address.city = "Vaihingen";
		Address savedAddress = addressRepository.save(person.address);
		
		Customer savedPerson = personRepository.save(person);
		assertEquals("Marty", savedPerson.name.firstname);
		assertEquals("Montana", savedPerson.name.lastname);
		assertEquals("Vaihingen", savedPerson.address.city);
		assertEquals(savedAddress, savedPerson.address);
	}
	
	@Test
	public void updatePerson() {
		Page<Customer> persons = personRepository.findByNameFirstnameAndNameLastname("Marty", "Maredo", PAGEABLE);
		Customer person = persons.getContent().get(0);
		person.name.lastname = "Montana";
		Customer savedPerson = personRepository.save(person);
		assertEquals("Marty", savedPerson.name.firstname);
		assertEquals("Montana", savedPerson.name.lastname);
	}

}
