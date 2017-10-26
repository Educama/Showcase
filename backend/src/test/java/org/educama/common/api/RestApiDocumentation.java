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
@SpringBootTest(classes = EducamaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
                fieldWithPath("customerTypeEnum").description("Tells wether the sender or receiver is the customer of the shipment"),
                fieldWithPath("shipmentCargo").description("Includes cargo information about the shipment"),
                fieldWithPath("shipmentCargo.numberPackages").description("The number of packages of the cargo"),
                fieldWithPath("shipmentCargo.totalWeight").description("The total weight of the cargo"),
                fieldWithPath("shipmentCargo.totalCapacity").description("The total capacity of cargo"),
                fieldWithPath("shipmentCargo.cargoDescription").description("The description of the cargo"),
                fieldWithPath("shipmentCargo.dangerousGoods").description("Is true if the cargo includes dangerous goods"),
                fieldWithPath("shipmentServices").description("Includes information about the Services of the shipment"),
                fieldWithPath("shipmentServices.preCarriage").description("Is true if additional actions have to take place before the shipment"),
                fieldWithPath("shipmentServices.exportInsurance").description("Is true if the shipment has export insurance"),
                fieldWithPath("shipmentServices.exportCustomsClearance").description("Is true if the shipment has to pay customs for export"),
                fieldWithPath("shipmentServices.flight").description("Is true if the shipment includes a flight"),
                fieldWithPath("shipmentServices.importInsurance").description("Is true if the shipment has import insurance"),
                fieldWithPath("shipmentServices.importCustomsClearance").description("Is true if the shipment has to pay customs for import"),
                fieldWithPath("shipmentServices.onCarriage").description("Is true if additional actions have to take place after the shipment")
        };


        // Task Resource

        fieldDescriptorTask = new FieldDescriptor[] {
                fieldWithPath("createTime").description("The create time of the task"),
                fieldWithPath("trackingId").description("The unique business key of the shipment mapped to the task"),
                fieldWithPath("taskId").description("The Id of the task"),
                fieldWithPath("name").description("The task name"),
                fieldWithPath("description").description("The task description"),
                fieldWithPath("assignee").description("The assignee of the task"),
                fieldWithPath("sender").description("The sender of the assigned shipment with address and name"),
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
                fieldWithPath("receiver.address.city").description("The city of the receiver's address")};

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
                                fieldWithPath("customerTypeEnum").description("Tells wether the sender or receiver is the customer of the shipment"),
                                fieldWithPath("shipmentCargo").description("Includes cargo information about the shipment"),
                                fieldWithPath("shipmentCargo.numberPackages").description("The number of packages of the cargo"),
                                fieldWithPath("shipmentCargo.totalWeight").description("The total weight of the cargo"),
                                fieldWithPath("shipmentCargo.totalCapacity").description("The total capacity of cargo"),
                                fieldWithPath("shipmentCargo.cargoDescription").description("The description of the cargo"),
                                fieldWithPath("shipmentCargo.dangerousGoods").description("Is true if the cargo includes dangerous goods"),
                                fieldWithPath("shipmentServices").description("Includes information about the Services of the shipment"),
                                fieldWithPath("shipmentServices.preCarriage").description("Is true if additional actions have to take place before the shipment"),
                                fieldWithPath("shipmentServices.exportInsurance").description("Is true if the shipment has export insurance"),
                                fieldWithPath("shipmentServices.exportCustomsClearance").description("Is true if the shipment has to pay customs for export"),
                                fieldWithPath("shipmentServices.flight").description("Is true if the shipment includes a flight"),
                                fieldWithPath("shipmentServices.importInsurance").description("Is true if the shipment has import insurance"),
                                fieldWithPath("shipmentServices.importCustomsClearance").description("Is true if the shipment has to pay customs for import"),
                                fieldWithPath("shipmentServices.onCarriage").description("Is true if additional actions have to take place after the shipment")),
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

        this.mockMvc.perform(get("/educama/v1/tasks/active"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(responseFields(
                                fieldWithPath("tasks[]").description("An array of active tasks")).andWithPrefix("tasks[].", fieldDescriptorTask)));
    }

    @Test
    public void listEnabledTasksTest() throws Exception {
        createShipment();

        this.mockMvc.perform(get("/educama/v1/tasks/enabled"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(responseFields(
                                fieldWithPath("tasks[]").description("An array of enabled tasks")).andWithPrefix("tasks[].", fieldDescriptorTask)));
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

    private String createCustomer(String name) throws Exception {
        MvcResult result = this.mockMvc
                .perform(post(CustomerController.CUSTOMER_RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(this.createCustomerResourceHashMap(name))))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        return jsonResult.getString("uuid");
    }

    private Map<String, Object> createCustomerResourceHashMap(String name) {
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

    private Map<String, Object>  createShipmentResourceHashMap() throws Exception {
        String uuidSender = createCustomer("Herbert Hollig");
        String uuidReceiver = createCustomer("Herbert Hollig");
        Map<String, Object> shipment = new LinkedHashMap<>();
        shipment.put("uuidSender", uuidSender);
        shipment.put("uuidReceiver", uuidReceiver);
        shipment.put("customerTypeEnum", "RECEIVER");

        Map<String, Object> cargo = new LinkedHashMap<>();
        cargo.put("numberPackages", "5");
        cargo.put("totalWeight", "40");
        cargo.put("totalCapacity", "32.5");
        cargo.put("cargoDescription", "this cargo includes pens and other writing articles");
        cargo.put("dangerousGoods", false);
        shipment.put("shipmentCargo", cargo);

        Map<String, Object> services = new LinkedHashMap<>();
        services.put("preCarriage", true);
        services.put("exportInsurance", false);
        services.put("exportCustomsClearance", true);
        services.put("flight", true);
        services.put("importInsurance", true);
        services.put("importCustomsClearance", false);
        services.put("onCarriage", true);
        shipment.put("shipmentServices", services);

        return shipment;
    }

    private String createPathExtension(Customer customer) {
        return "/" + customer.uuid;
    }

}
