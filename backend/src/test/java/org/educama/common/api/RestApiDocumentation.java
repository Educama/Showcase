package org.educama.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.educama.EducamaApplication;
import org.educama.customer.api.CustomerController;
import org.educama.customer.api.resource.NameMapper;
import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.customer.model.Name;
import org.educama.shipment.api.ShipmentController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class uses Spring Rest Docs to generate example requests which are
 * embedded into asciidoc/rest-api.adoc. The result is transformend to HTML and
 * served at /docs/rest-api.html
 *
 * Documentation:
 * http://docs.spring.io/spring-restdocs/docs/current/reference/html5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EducamaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiDocumentation {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private CustomerBoundaryService customerService;

	private Customer customerOne;

	private MockMvc mockMvc;

	private RestDocumentationResultHandler documentationHandler;

	private FieldDescriptor[] fieldDescriptorShipment;

	private FieldDescriptor[] fieldDescriptorCreateCustomerRequest;

	private FieldDescriptor[] fieldDescriptorCustomerRequest;

	private FieldDescriptor[] fieldDescriptorCustomerResponse;

	private FieldDescriptor[] fieldDescriptorCustomerList;

	@Before
	public void setUp() {
		this.customerOne = createSampleCustomers("Lars", "Dülfer", createAddress());

		this.documentationHandler = document("{methodName}", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withPort(8081))
				.alwaysDo(this.documentationHandler).build();

		fieldDescriptorShipment = new FieldDescriptor[] {
				fieldWithPath("id").description("The unique database identifier"),
				fieldWithPath("trackingId").description("The unique business key of the shipment"),
				fieldWithPath("customer").description("The name of the customer"),
				fieldWithPath("senderAddress").description("The address of the sender"),
				fieldWithPath("receiverAddress").description("The address of the final receiver") };

		fieldDescriptorCreateCustomerRequest = new FieldDescriptor[] {
				fieldWithPath("name").description("The name of the customer"),
				fieldWithPath("address").description("The address of the customer"),
				fieldWithPath("address.street").description("The street of the customer's address"),
				fieldWithPath("address.streetNo").description("The street number of the customer's address"),
				fieldWithPath("address.zipCode").description("The zip code of the customer's address"),
				fieldWithPath("address.city").description("The city of the customer's address") };

		fieldDescriptorCustomerRequest = ArrayUtils.add(fieldDescriptorCreateCustomerRequest,
				fieldWithPath("uuid").description("business key of customer"));

		FieldDescriptor[] fieldDescriptorCustomerResponseAdditional = new FieldDescriptor[] {
				fieldWithPath("uuid").description("business key of customer"),
				fieldWithPath("_links").description("Links section"),
				fieldWithPath("_links.self").description("Link to self"),
				fieldWithPath("_links.self.href").description("Link to instance") };

		fieldDescriptorCustomerResponse = ArrayUtils.addAll(fieldDescriptorCreateCustomerRequest,
				fieldDescriptorCustomerResponseAdditional);

		fieldDescriptorCustomerList = new FieldDescriptor[] {
				fieldWithPath("pageNumber").description("Number of the actual page"),

				fieldWithPath("pageSize").description("Number of elements on page"),
				fieldWithPath("totalPages").description("Number of pages"),
				fieldWithPath("totalElements").description("Number of entries in response"),
				fieldWithPath("customers[]").description("An array of customer objects") };
	}

	@Test
	public void createShipment() throws Exception {
		Map<String, String> shipment = new LinkedHashMap<>();
		shipment.put("customer", "NovaTec Consulting GmbH");
		shipment.put("senderAddress", "Dieselstr. 18/1, 70771 Leinfelden-Echterdingen, Germany");
		shipment.put("receiverAddress", "Santa Claus Main Post Office, FI-96930 Arctic Circle, Finland");

		this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(shipment))).andExpect(status().isCreated()).andDo(
						this.documentationHandler.document(
								requestFields(fieldWithPath("customer").description("The name of the customer"),
										fieldWithPath("senderAddress").description("The address of the sender"),
										fieldWithPath("receiverAddress")
												.description("The address of the final receiver")),
								responseFields(fieldDescriptorShipment)));
	}

	@Test
	public void listShipment() throws Exception {
		this.mockMvc.perform(get(ShipmentController.SHIPMENT_RESOURCE_PATH)).andExpect(status().isOk())
				.andDo(this.documentationHandler.document(
						responseFields(fieldWithPath("shipments[]").description("An array of shipment objects"))
								.andWithPrefix("shipments[].", fieldDescriptorShipment)));

	}

	@Test
	public void createCustomer() throws Exception {
		Map<String, Object> customer = new LinkedHashMap<>();
		customer.put("name", "John Doe");
		Map<String, String> address = new LinkedHashMap<>();
		address.put("street", "Normal street");
		address.put("streetNo", "234");
		address.put("zipCode", "10000");
		address.put("city", "Nowhere");
		customer.put("address", address);

		this.mockMvc
				.perform(post(CustomerController.CUSTOMER_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(customer)))
				.andExpect(status().isCreated())
				.andDo(this.documentationHandler.document(requestFields(fieldDescriptorCreateCustomerRequest),
						responseFields(fieldDescriptorCustomerResponse)));

	}

	@Test
	public void getSingleCustomer() throws Exception {
		this.mockMvc.perform(get(CustomerController.CUSTOMER_RESOURCE_PATH + createPathExtension(customerOne)))
				.andExpect(status().isOk())
				.andDo(this.documentationHandler.document(responseFields(fieldDescriptorCustomerResponse)));

	}

	@Test
	public void listCustomers() throws Exception {
		this.mockMvc.perform(get(CustomerController.CUSTOMER_RESOURCE_PATH)).andExpect(status().isOk())
				.andDo(this.documentationHandler.document(responseFields(fieldDescriptorCustomerList)
						.andWithPrefix("customers[].", fieldDescriptorCustomerResponse)));

	}

	@Test
	public void deleteCustomer() throws Exception {
		this.mockMvc.perform(delete(CustomerController.CUSTOMER_RESOURCE_PATH + createPathExtension(customerOne)))
				.andExpect(status().isOk());
	}

	private String createPathExtension(Customer customer) {
		return "/" + customer.getId();
	}

	@Test
	public void updateCustomer() throws Exception {
		Map<String, Object> customer = new LinkedHashMap<>();
		customer.put("name", NameMapper.toString(customerOne.name));
		customer.put("uuid", customerOne.uuid);
		Map<String, String> address = new LinkedHashMap<>();
		address.put("street", customerOne.address.street);
		address.put("streetNo", "18/2");
		address.put("zipCode", customerOne.address.zipCode);
		address.put("city", customerOne.address.city);
		customer.put("address", address);

		this.mockMvc
				.perform(put(CustomerController.CUSTOMER_RESOURCE_PATH + createPathExtension(customerOne))
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(customer)))
				.andExpect(status().isOk())
				.andDo(this.documentationHandler.document(requestFields(fieldDescriptorCustomerRequest),
						responseFields(fieldDescriptorCustomerResponse)));

	}

	private Customer createSampleCustomers(String firstname, String lastname, Address address) {
		Customer customer = new Customer();
		customer.name = new Name(firstname, lastname);
		customer.address = address;
		customerService.createCustomer(customer);
		return customer;
	}

	private Address createAddress() {
		Address address = new Address();
		address.street = "Dieselstr";
		address.streetNo = "18/1";
		address.zipCode = "70771";
		address.city = "Leinfelden-Echterdingen";
		return address;
	}
}
