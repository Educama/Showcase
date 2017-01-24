package org.educama;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.educama.customer.api.CustomerController;
import org.educama.shipment.api.ShipmentController;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class uses Spring Rest Docs to generate example requests which are embedded into asciidoc/rest-api.adoc.
 * The result is transformend to HTML and served at /docs/rest-api.html
 *
 * Documentation: http://docs.spring.io/spring-restdocs/docs/current/reference/html5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EducamaApplication.class, webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiDocumentationTest {
	
	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private RestDocumentationResultHandler documentationHandler;

	private FieldDescriptor[] fieldDescriptorShipment;
	
	private FieldDescriptor[] fieldDescriptorPerson;
	
	private FieldDescriptor[] fieldDescriptorAddress;

	@Before
	public void setUp() {
		this.documentationHandler = document("{methodName}",
				preprocessRequest(
						prettyPrint(),
						removeHeaders("Host")),
				preprocessResponse(prettyPrint()));

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(this.documentationHandler)
				.build();

		fieldDescriptorShipment = new FieldDescriptor[] {
				fieldWithPath("id").description("The unique database identifier"),
				fieldWithPath("trackingId").description("The unique business key of the shipment"),
				fieldWithPath("customer").description("The name of the customer"),
				fieldWithPath("senderAddress").description("The address of the sender"),
				fieldWithPath("receiverAddress").description("The address of the final receiver") };
		
		fieldDescriptorPerson = new FieldDescriptor[] {
				fieldWithPath("id").description("The unique database identifier"),
				fieldWithPath("name").description("The name of the person"),
				fieldWithPath("address").description("The address of the person (if exists)") };
		
		fieldDescriptorAddress = new FieldDescriptor[] {
				fieldWithPath("id").description("The unique database identifier"),
				fieldWithPath("street").description("The name of the street"),
				fieldWithPath("streetNo").description("The number of the street"),
				fieldWithPath("zipCode").description("The zipCode of the city"),
				fieldWithPath("city").description("The name of the city") };
	}

	@Test
	public void createShipment() throws Exception {
		Map<String,String> shipment = new LinkedHashMap<>();
		shipment.put("customer", "NovaTec Consulting GmbH");
		shipment.put("senderAddress", "Dieselstr. 18/1, 70771 Leinfelden-Echterdingen, Germany");
		shipment.put("receiverAddress", "Santa Claus Main Post Office, FI-96930 Arctic Circle, Finland");
		this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(shipment)))
				.andExpect(status().isCreated())
				.andDo(this.documentationHandler.document(
						requestFields(
								fieldWithPath("customer").description("The name of the customer"),
								fieldWithPath("senderAddress").description("The address of the sender"),
								fieldWithPath("receiverAddress").description("The address of the final receiver")
						),
						responseFields(fieldDescriptorShipment)
				));
	}

	@Test
	public void listShipment() throws Exception {
		this.mockMvc.perform(get(ShipmentController.SHIPMENT_RESOURCE_PATH))
			.andExpect(status().isOk())
			.andDo(this.documentationHandler.document(
					responseFields(
							fieldWithPath("shipments[]").description("An array of shipment objects")).andWithPrefix("shipments[].", fieldDescriptorShipment)));
	}
	
	@Test
	@Ignore
	public void listCustomers() throws Exception {
		this.mockMvc.perform(get(CustomerController.CUSTOMER_RESOURCE_PATH))
			.andExpect(status().isOk())
			.andDo(this.documentationHandler.document(
					responseFields(
							fieldWithPath("customers[]").description("An array of customer objects")).andWithPrefix("customers[].", fieldDescriptorPerson)));

	}

	// TODO: Add test for create Customer, delete Persone and update Customer
}
