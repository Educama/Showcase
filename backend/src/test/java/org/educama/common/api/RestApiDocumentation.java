package org.educama.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.educama.EducamaApplication;
import org.educama.customer.api.CustomerController;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.shipment.api.ShipmentController;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
 * <p>
 * Documentation:
 * http://docs.spring.io/spring-restdocs/docs/current/reference/html5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EducamaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiDocumentation {

    public static final int PORT = 8081;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private Customer customerOne;

    private MockMvc mockMvc;

    private RestDocumentationResultHandler documentationHandler;

    private FieldDescriptor[] fieldDescriptorShipmentResource;

    private FieldDescriptor[] fieldDescriptorTask;

    private FieldDescriptor[] fieldDescriptorSaveCustomerResource;

    private FieldDescriptor[] fieldDescriptorCustomerResource;

    private FieldDescriptor[] fieldDescriptorCustomerListResource;

    @Before
    public void setUp() {
        Address address = new Address("Dieselstr", "18/1", "70771", "Leinfelden-Echterdingen");
        this.customerOne = new Customer("Max Mueller", address);

        this.documentationHandler = document("{methodName}", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris().withPort(PORT))
                .alwaysDo(this.documentationHandler).build();

        // Generic Field Descriptions

        FieldDescriptor[] fieldDescriptorSelfLink = new FieldDescriptor[]{
                fieldWithPath("_links").description("Links section"),
                fieldWithPath("_links.self").description("Link to self"),
                fieldWithPath("_links.self.href").description("Link to instance")};

        // Shipment Resource

        fieldDescriptorShipmentResource = new FieldDescriptor[]{

                fieldWithPath("trackingId").description("The unique business key of the shipment"),
                fieldWithPath("uuidSender").description("the UUID of the sender"),
                fieldWithPath("uuidReceiver").description("the UUID of the receiver"),
                fieldWithPath("sender").description("The sender of the shipment with address and name"),
                fieldWithPath("sender.name").description("The name of the sender"),
                fieldWithPath("sender.address").description("The address of the sender"),
                fieldWithPath("sender.address.street").description("The street of the sender's address"),
                fieldWithPath("sender.address.streetNo").description("The street number of the sender's address"),
                fieldWithPath("sender.address.zipCode").description("The zip code of the sender's address"),
                fieldWithPath("sender.address.city").description("The city of the sender's address"),
                fieldWithPath("receiver").description("The receiver of the shipment with address and name"),
                fieldWithPath("sender.name").description("The name of the receiver"),
                fieldWithPath("sender.address").description("The address of the receiver"),
                fieldWithPath("sender.address.street").description("The street of the receiver's address"),
                fieldWithPath("sender.address.streetNo").description("The street number of the receiver's address"),
                fieldWithPath("sender.address.zipCode").description("The zip code of the receiver's address"),
                fieldWithPath("sender.address.city").description("The city of the receiver's address"),
                fieldWithPath("senderIsEmployer").description("Is true if the sender is the Employer of the shipment"),
                fieldWithPath("receiverIsEmployer").description("Is true if the receiver is the Employer of the shipment"),
                fieldWithPath("numberPackages").description("The number of the packages"),
                fieldWithPath("totalWeight").description("The total weight of cargo"),
                fieldWithPath("totalCapacity").description("The total capacity of cargo"),
                fieldWithPath("cargoDescription").description("The description of the cargo"),
                fieldWithPath("dangerousGoods").description("Is true if the cargo includes dangerous goods"),
                fieldWithPath("forerun").description("Is true if additional actions have to take place before the shipment"),
                fieldWithPath("exportInsurance").description("Is true if the shipment has export insurance"),
                fieldWithPath("exportCustomsClearance").description("Is true if the shipment has to pay customs for export"),
                fieldWithPath("flight").description("Is true if the shipment includes a flight"),
                fieldWithPath("importInsurance").description("Is true if the shipment has import insurance"),
                fieldWithPath("importCustomsClearance").description("Is true if the shipment has to pay customs for import"),
                fieldWithPath("backlash").description("Is true if additional actions have to take place after the shipment")
        };


        // Task Resource

        fieldDescriptorTask = new FieldDescriptor[] {
                fieldWithPath("createTime").description("The create time of the task"),
                fieldWithPath("trackingId").description("The unique business key of the shipment mapped to the task"),
                fieldWithPath("taskId").description("The Id of the task"),
                fieldWithPath("name").description("The task name"),
                fieldWithPath("description").description("The task description"),
                fieldWithPath("assignee").description("The assignee of the task"),
                fieldWithPath("customer").description("The shipments customer name") };

        // Customer Resource

        fieldDescriptorSaveCustomerResource = new FieldDescriptor[]{
                fieldWithPath("name").description("The name of the customer"),
                fieldWithPath("address").description("The address of the customer"),
                fieldWithPath("address.street").description("The street of the customer's address"),
                fieldWithPath("address.streetNo").description("The street number of the customer's address"),
                fieldWithPath("address.zipCode").description("The zip code of the customer's address"),
                fieldWithPath("address.city").description("The city of the customer's address")};

        fieldDescriptorCustomerResource =
                ArrayUtils.addAll(
                    ArrayUtils.add(
                            fieldDescriptorSaveCustomerResource,
                            fieldWithPath("uuid").description("The identifier of the resource")),
                    fieldDescriptorSelfLink);

        fieldDescriptorCustomerListResource = new FieldDescriptor[]{
                fieldWithPath("pageNumber").description("Number of the actual page"),

                fieldWithPath("pageSize").description("Number of elements on page"),
                fieldWithPath("totalPages").description("Number of pages"),
                fieldWithPath("totalElements").description("Number of entries in response"),
                fieldWithPath("customers[]").description("An array of customer objects")};

    }

    @Test
    public void createShipmentTest() throws Exception {
        createShipment()
        .andExpect(status().isCreated()).andDo(
                this.documentationHandler.document(
                        requestFields(fieldWithPath("uuidSender").description("the UUID of the sender"),
                                fieldWithPath("uuidReceiver").description("the UUID of the receiver"),
                                fieldWithPath("sender").description("The sender of the shipment with address and name"),
                                fieldWithPath("sender.name").description("The name of the sender"),
                                fieldWithPath("sender.address").description("The address of the sender"),
                                fieldWithPath("sender.address.street").description("The street of the sender's address"),
                                fieldWithPath("sender.address.streetNo").description("The street number of the sender's address"),
                                fieldWithPath("sender.address.zipCode").description("The zip code of the sender's address"),
                                fieldWithPath("sender.address.city").description("The city of the sender's address"),
                                fieldWithPath("receiver").description("The receiver of the shipment with address and name"),
                                fieldWithPath("receiver.name").description("The name of the receiver"),
                                fieldWithPath("receiver.address").description("The address of the receiver"),
                                fieldWithPath("receiver.address.street").description("The street of the receiver's address"),
                                fieldWithPath("receiver.address.streetNo").description("The street number of the receiver's address"),
                                fieldWithPath("receiver.address.zipCode").description("The zip code of the receiver's address"),
                                fieldWithPath("receiver.address.city").description("The city of the receiver's address"),
                                fieldWithPath("senderIsEmployer").description("Is true if the sender is the Employer of the shipment"),
                                fieldWithPath("receiverIsEmployer").description("Is true if the receiver is the Employer of the shipment"),
                                fieldWithPath("numberPackages").description("The number of the packages"),
                                fieldWithPath("totalWeight").description("The total weight of cargo"),
                                fieldWithPath("totalCapacity").description("The total capacity of cargo"),
                                fieldWithPath("cargoDescription").description("The description of the cargo"),
                                fieldWithPath("dangerousGoods").description("Is true if the cargo includes dangerous goods"),
                                fieldWithPath("forerun").description("Is true if additional actions have to take place before the shipment"),
                                fieldWithPath("exportInsurance").description("Is true if the shipment has export insurance"),
                                fieldWithPath("exportCustomsClearance").description("Is true if the shipment has to pay customs for export"),
                                fieldWithPath("flight").description("Is true if the shipment includes a flight"),
                                fieldWithPath("importInsurance").description("Is true if the shipment has import insurance"),
                                fieldWithPath("importCustomsClearance").description("Is true if the shipment has to pay customs for import"),
                                fieldWithPath("backlash").description("Is true if additional actions have to take place after the shipment")),
                        responseFields(fieldDescriptorShipmentResource)));
}
    @Test
    public void listShipmentTest() throws Exception {
        createShipment();

        this.mockMvc.perform(get(ShipmentController.SHIPMENT_RESOURCE_PATH)).andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(fieldWithPath("shipments[]").description("An array of shipment objects"))
                                .andWithPrefix("shipments[].", fieldDescriptorShipmentResource)));

    }

    @Test
    public void listTasksTest() throws Exception {
        createShipment();

        this.mockMvc.perform(get("/educama/v1/tasks"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(responseFields(
                                fieldWithPath("tasks[]").description("An array of task objects")).andWithPrefix("tasks[].", fieldDescriptorTask)));
    }

    @Test
    public void createCustomerTest() throws Exception {
        this.mockMvc
                .perform(post(CustomerController.CUSTOMER_RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(this.createCustomerResourceHashMap("John Doe"))))
                .andExpect(status().isCreated())
                .andDo(this.documentationHandler
                        .document(
                                requestFields(fieldDescriptorSaveCustomerResource),
                                responseFields(fieldDescriptorCustomerResource))
                );
    }

    @Test
    public void updateCustomerTest() throws Exception {
        String uuid = this.createCustomer("Donald Duck");
        Map<String, Object> updatedCustomer = createCustomerResourceHashMap("Daisy Duck");

        this.mockMvc
                .perform(put(CustomerController.CUSTOMER_RESOURCE_PATH + "/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(updatedCustomer)))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(
                                requestFields(fieldDescriptorSaveCustomerResource),
                                responseFields(fieldDescriptorCustomerResource))
                );
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        String uuid = this.createCustomer("Dagobert Duck");

        this.mockMvc
                .perform(delete(CustomerController.CUSTOMER_RESOURCE_PATH + "/" + uuid))
                .andExpect(status().isOk());
    }

    @Test
    public void getSingleCustomerTest() throws Exception {
        String uuid = this.createCustomer("Max Mueller");

        this.mockMvc
                .perform(get(CustomerController.CUSTOMER_RESOURCE_PATH + "/" + uuid))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(
                                responseFields(fieldDescriptorCustomerResource)));
    }

    @Test
    public void listCustomersTest() throws Exception {
        this.createCustomer("Simon Schmidt");

        this.mockMvc
                .perform(get(CustomerController.CUSTOMER_RESOURCE_PATH))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(
                                responseFields(fieldDescriptorCustomerListResource)
                                        .andWithPrefix("customers[].", fieldDescriptorCustomerResource))
                );
    }

    @Test
    public void suggestCustomersTest() throws Exception {
        String uuid = this.createCustomer("Steve Schmitt");

        this.mockMvc
                .perform(get(CustomerController.CUSTOMER_RESOURCE_PATH + "/suggestions")
                .param("term", "ste"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(
                                responseFields(fieldDescriptorCustomerListResource)
                                        .andWithPrefix("customers[].", fieldDescriptorCustomerResource))
                );
    }

    private ResultActions createShipment() throws Exception {

        return this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.createShipmentResourceHashMap())));

    }

    private String createCustomer(String name) throws  Exception {
        MvcResult result = this.mockMvc
                .perform(post(CustomerController.CUSTOMER_RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(this.createCustomerResourceHashMap(name))))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        return jsonResult.getString("uuid");
    }

    private Map<String, Object>  createCustomerResourceHashMap(String name) {
        Map<String, Object> customer = new LinkedHashMap<>();
        customer.put("name", name);
        Map<String, String> address = new LinkedHashMap<>();
        address.put("street", "Normal street");
        address.put("streetNo", "234");
        address.put("zipCode", "10000");
        address.put("city", "Nowhere");
        customer.put("address", address);
        return customer;
    }

    private Map<String, Object>  createShipmentResourceHashMap() {
        Map<String, Object> sender = createCustomerResourceHashMap("Hans Kümmerle");
        Map<String, Object> receiver = createCustomerResourceHashMap("Albert Stein");
        String uuidSender = null;
        String uuidReceiver = null;
        try {
            String uuidForSender = createCustomer("Herbert Hollig");
            uuidSender = uuidForSender;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            String uuidForReceiver = createCustomer("Herbert Hollig");
            uuidReceiver = uuidForReceiver;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Object> shipment = new LinkedHashMap<>();
        shipment.put("uuidSender", uuidSender);
        shipment.put("uuidReceiver", uuidReceiver);
        shipment.put("sender", sender);
        shipment.put("receiver", receiver);
        shipment.put("senderIsEmployer", true);
        shipment.put("receiverIsEmployer", false);
        shipment.put("numberPackages", "5");
        shipment.put("totalWeight", "40");
        shipment.put("totalCapacity", "32.5");
        shipment.put("cargoDescription", "this cargo includes pens and other writing articles");
        shipment.put("dangerousGoods", false);
        shipment.put("forerun", true);
        shipment.put("exportInsurance", false);
        shipment.put("exportCustomsClearance", true);
        shipment.put("flight", true);
        shipment.put("importInsurance", true);
        shipment.put("importCustomsClearance", false);
        shipment.put("backlash", true);

        return shipment;
    }

    private String createPathExtension(Customer customer) {
        return "/" + customer.uuid;
    }

}
