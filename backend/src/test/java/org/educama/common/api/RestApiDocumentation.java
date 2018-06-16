package org.educama.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.task.Task;
import org.educama.EducamaApplication;
import org.educama.customer.api.CustomerController;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.educama.shipment.api.ShipmentController;
import org.educama.shipment.process.ShipmentCaseConstants;
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

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
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
    private static final double FLIGHTPRICE = 100.12;

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

    private FieldDescriptor[] fieldDescriptorActiveTask;

    private FieldDescriptor[] fieldDescriptorEnabledTask;

    private FieldDescriptor[] fieldDescriptorTask;

    private FieldDescriptor[] fieldDescriptorCompletedTaskResource;

    private FieldDescriptor[] fieldDescriptorSaveCustomerResource;

    private FieldDescriptor[] fieldDescriptorCustomerResource;

    private FieldDescriptor[] fieldDescriptorCustomerListResource;

    private FieldDescriptor[] fieldDescriptorInvoiceResource;

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
                fieldWithPath("shipmentServices.onCarriage").description("Is true if additional actions have to take place after the shipment"),
                fieldWithPath("shipmentFlight").description("Includes information about the shipmentFlight"),
                fieldWithPath("shipmentFlight.flightNumber").description("The number of the flight"),
                fieldWithPath("shipmentFlight.airline").description("The airline of the flight"),
                fieldWithPath("shipmentFlight.departureAirport").description("The departure airport of the flight"),
                fieldWithPath("shipmentFlight.destinationAirport").description("The destination airport of the flight"),
                fieldWithPath("shipmentFlight.departureTime").description("The time when the flight starts"),
                fieldWithPath("shipmentFlight.destinationTime").description("The time when the flight lands"),
                fieldWithPath("shipmentFlight.price").description("The price of the flight"),
                fieldWithPath("status").description("The status of the shipment")
        };

        // Active Task Resource
        fieldDescriptorActiveTask = new FieldDescriptor[] {
                fieldWithPath("createTime").description("The create time of the task"),
                fieldWithPath("dueDate").description("The due date of the task"),
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
                fieldWithPath("receiver.address.city").description("The city of the receiver's address")
        };

        // Enabled Task Resource
        fieldDescriptorEnabledTask = new FieldDescriptor[] {
                fieldWithPath("trackingId").description("The unique business key of the shipment mapped to the task"),
                fieldWithPath("id").description("The Id of the task"),
                fieldWithPath("name").description("The task name"),
                fieldWithPath("description").description("The task description"),
                fieldWithPath("type").description("The type of the task")
        };

        // Task Resource

        fieldDescriptorTask = new FieldDescriptor[] {
                fieldWithPath("createTime").description("The create time of the task"),
                fieldWithPath("dueDate").description("The due date of the task"),
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

        // Completed Task Resource

        fieldDescriptorCompletedTaskResource = new FieldDescriptor[]{
                fieldWithPath("trackingId").description("The unique business key of the shipment mapped to the task"),
                fieldWithPath("taskId").description("The Id of the task"),
                fieldWithPath("name").description("The task name"),
                fieldWithPath("description").description("The task description"),
                fieldWithPath("assignee").description("The assignee of the task"),
                fieldWithPath("endTime").description("The time when the task was completed")};

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

        // Invoice Resource
         fieldDescriptorInvoiceResource = new FieldDescriptor[]{
                 fieldWithPath("invoiceNumber").description("The Number of the invoice"),
                 fieldWithPath("invoiceCreationDate").description("The Invoice creation date"),
                 fieldWithPath("preCarriage").description("The price of the pre-carriage"),
                 fieldWithPath("exportInsurance").description("The price of the export insurance"),
                 fieldWithPath("exportCustomsClearance").description("The price of the export customs clearance"),
                 fieldWithPath("flightPrice").description("The price of the flight"),
                 fieldWithPath("importInsurance").description("The price of the import insurance"),
                 fieldWithPath("importCustomsClearance").description("The price of the import customs clearance"),
                 fieldWithPath("onCarriage").description("The price of the on-carriage"),
                 fieldWithPath("managementFee").description("The price of the management fee"),
                 fieldWithPath("serviceFee").description("The price of the service fee"),
                 fieldWithPath("discount").description("The price of the discount"),

         };

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
                                fieldWithPath("shipmentServices.onCarriage").description("Is true if additional actions have to take place after the shipment"),
                                fieldWithPath("shipmentFlight").description("Includes information about the shipmentFlight"),
                                fieldWithPath("shipmentFlight.flightNumber").description("The number of the flight"),
                                fieldWithPath("shipmentFlight.airline").description("The airline of the flight"),
                                fieldWithPath("shipmentFlight.departureAirport").description("The departure airport of the flight"),
                                fieldWithPath("shipmentFlight.destinationAirport").description("The destination airport of the flight"),
                                fieldWithPath("shipmentFlight.departureTime").description("The time when the flight starts"),
                                fieldWithPath("shipmentFlight.destinationTime").description("The time when the flight lands"),
                                fieldWithPath("shipmentFlight.price").description("The price of the flight")),
                        responseFields(fieldDescriptorShipmentResource)));
    }

    @Test
    public void updateShipmentTest() throws Exception {
        updateShipment()
                .andExpect(status().isOk()).andDo(
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
                                fieldWithPath("shipmentServices.onCarriage").description("Is true if additional actions have to take place after the shipment"),
                                fieldWithPath("shipmentFlight").description("Includes information about the shipmentFlight"),
                                fieldWithPath("shipmentFlight.flightNumber").description("The number of the flight"),
                                fieldWithPath("shipmentFlight.airline").description("The airline of the flight"),
                                fieldWithPath("shipmentFlight.departureAirport").description("The departure airport of the flight"),
                                fieldWithPath("shipmentFlight.destinationAirport").description("The destination airport of the flight"),
                                fieldWithPath("shipmentFlight.departureTime").description("The time when the flight starts"),
                                fieldWithPath("shipmentFlight.destinationTime").description("The time when the flight lands"),
                                fieldWithPath("shipmentFlight.price").description("The price of the flight")),
                        responseFields(fieldDescriptorShipmentResource)));
    }

    @Test
    public void addFlightToShipmentTest() throws Exception {
        addFlightToShipment()
                .andExpect(status().isOk()).andDo(
                this.documentationHandler.document(
                        requestFields(
                                fieldWithPath("shipmentFlight").description("Includes information about the shipmentFlight"),
                                fieldWithPath("shipmentFlight.flightNumber").description("The number of the flight"),
                                fieldWithPath("shipmentFlight.airline").description("The airline of the flight"),
                                fieldWithPath("shipmentFlight.departureAirport").description("The departure airport of the flight"),
                                fieldWithPath("shipmentFlight.destinationAirport").description("The destination airport of the flight"),
                                fieldWithPath("shipmentFlight.departureTime").description("The time when the flight starts"),
                                fieldWithPath("shipmentFlight.destinationTime").description("The time when the flight lands"),
                                fieldWithPath("shipmentFlight.price").description("The price of the flight")),
                        responseFields(fieldDescriptorShipmentResource)));
    }

    @Test
    public void getShipmentTest() throws Exception {
        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();
        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");


        this.mockMvc.perform(get("/educama/v1/shipments/" + trackingId)).andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
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
                                fieldWithPath("tasks[]").description("An array of task objects")).andWithPrefix("tasks[].", fieldDescriptorTask)));
    }

    @Test
    public void getActiveTasksForShipmentTest() throws Exception {
        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");


        this.mockMvc.perform(get("/educama/v1/tasks/active/" + trackingId))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(responseFields(
                                fieldWithPath("tasks[]").description("An array of active tasks objects"))
                                .andWithPrefix("tasks[].", fieldDescriptorActiveTask)));
    }

    @Test
    public void getEnabledTasksTest() throws Exception {
        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");

        this.mockMvc.perform(get("/educama/v1/tasks/enabled/" + trackingId))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler
                        .document(responseFields(
                                fieldWithPath("tasks[]").description("An array of enabled tasks objects"))
                                .andWithPrefix("tasks[].", fieldDescriptorEnabledTask)));
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

    @Test
    public void getCompletedTasksTest() throws Exception {

        MvcResult result = this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.createIncompleteShipmentResourceHashMap())))
                .andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId =  jsonResult.getString("trackingId");

        CaseExecution completeShipmentOrderCaseExecution = processEngine().getCaseService().createCaseExecutionQuery()
                .activityId(ShipmentCaseConstants.PLAN_ITEM_HUMAN_TASK_COMPLETE_SHIPMENT_ORDER)
                .caseInstanceBusinessKey(trackingId).singleResult();

        // Complete task 'Complete shipment order'
        Task task = processEngine().getTaskService().createTaskQuery()
                .caseExecutionId(completeShipmentOrderCaseExecution.getId()).singleResult();

        processEngine().getTaskService().complete(task.getId());

        //Get Completed Task
        this.mockMvc.perform(get("/educama/v1/tasks/completed" + "/" + trackingId))
                    .andExpect(status().isOk())
                    .andDo(this.documentationHandler
                            .document(
                                    responseFields(fieldWithPath("tasks[]").description("An array of completed task objects"))
                                            .andWithPrefix("tasks[].", fieldDescriptorCompletedTaskResource))
                    );
    }

    @Test
    public void listInvoicesTest() throws Exception {
        ResultActions result = createInvoice();
        String requestURI = result.andReturn().getRequest().getRequestURI();

        this.mockMvc.perform(get(requestURI)).andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(fieldWithPath("invoices[]").description("An array of invoice objects"))
                                .andWithPrefix("invoices[].", fieldDescriptorInvoiceResource)));
    }

    @Test
    public void createInvoiceTest() throws Exception {
        createInvoice()
                .andExpect(status().isOk()).andDo(
                this.documentationHandler.document(
                        requestFields(
                                fieldWithPath("invoiceCreationDate").description("The Invoice creation date"),
                                fieldWithPath("preCarriage").description("The price of the pre-carriage"),
                                fieldWithPath("exportInsurance").description("The price of the export insurance"),
                                fieldWithPath("exportCustomsClearance").description("The price of the export customs clearance"),
                                fieldWithPath("flightPrice").description("The price of the flight"),
                                fieldWithPath("importInsurance").description("The price of the import insurance"),
                                fieldWithPath("importCustomsClearance").description("The price of the import customs clearance"),
                                fieldWithPath("onCarriage").description("The price of the on-carriage"),
                                fieldWithPath("managementFee").description("The price of the management fee"),
                                fieldWithPath("serviceFee").description("The price of the service fee"),
                                fieldWithPath("discount").description("The price of the discount")),
                        responseFields(fieldDescriptorInvoiceResource)));
    }

    private ResultActions createShipment() throws Exception {

        return this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.createShipmentResourceHashMap())));

    }

    private ResultActions updateShipment() throws Exception {

        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");

        return this.mockMvc.perform(put(ShipmentController.SHIPMENT_RESOURCE_PATH + "/flight/" + trackingId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.updateShipmentResourceHashMap())));
    }

    private ResultActions addFlightToShipment() throws Exception {

        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");

        return this.mockMvc.perform(put(ShipmentController.SHIPMENT_RESOURCE_PATH + "/flight/" + trackingId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.addFlightToShipmentResourceHashMap())));
    }

    private ResultActions createInvoice() throws Exception {


        MvcResult result = createShipment().andExpect(status().isCreated()).andReturn();

        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        String trackingId = jsonResult.getString("trackingId");

        // Get task 'Organize Flight'
        CaseExecution organizeFlightOrderCaseExecution = processEngine().getCaseService().createCaseExecutionQuery()
                .activityId(ShipmentCaseConstants.PLAN_ITEM_HUMAN_TASK_ORGANIZE_FLIGHT)
                .caseInstanceBusinessKey(trackingId).singleResult();

        // Complete task 'Organize Flight'
        Task task = processEngine().getTaskService().createTaskQuery()
                .caseExecutionId(organizeFlightOrderCaseExecution.getId()).singleResult();
        processEngine().getTaskService().complete(task.getId());

        return this.mockMvc.perform(post(ShipmentController.SHIPMENT_RESOURCE_PATH + "/invoice/" + trackingId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(this.createInvoiceResourceHashMap())));

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

        Map<String, Object> flight = new LinkedHashMap<>();
        flight.put("flightNumber", "10243");
        flight.put("airline", "LH");
        flight.put("departureAirport", "FRA");
        flight.put("destinationAirport", "STR");
        flight.put("departureTime", "2015-06-02T21:34:33.616Z");
        flight.put("destinationTime", "2015-06-02T21:34:33.616Z");
        flight.put("price", FLIGHTPRICE);
        shipment.put("shipmentFlight", flight);

        return shipment;
    }

    private Map<String, Object>  updateShipmentResourceHashMap() throws Exception {
        String uuidSender = createCustomer("Herbert Update");
        String uuidReceiver = createCustomer("Herbert Update");
        Map<String, Object> shipment = new LinkedHashMap<>();
        shipment.put("uuidSender", uuidSender);
        shipment.put("uuidReceiver", uuidReceiver);
        shipment.put("customerTypeEnum", "RECEIVER");

        Map<String, Object> cargo = new LinkedHashMap<>();
        cargo.put("numberPackages", "55");
        cargo.put("totalWeight", "400");
        cargo.put("totalCapacity", "322.5");
        cargo.put("cargoDescription", "this cargo includes pens and other writing articles Update");
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

        Map<String, Object> flight = new LinkedHashMap<>();
        flight.put("flightNumber", "999999");
        flight.put("airline", "LH");
        flight.put("departureAirport", "UPD");
        flight.put("destinationAirport", "UPD");
        flight.put("departureTime", "2015-06-02T21:34:33.616Z");
        flight.put("destinationTime", "2015-06-02T21:34:33.616Z");
        flight.put("price", FLIGHTPRICE);
        shipment.put("shipmentFlight", flight);

        return shipment;
    }

    private Map<String, Object> addFlightToShipmentResourceHashMap() throws Exception {

        Map<String, Object> flight = new LinkedHashMap<>();
        Map<String, Object> shipmentFlight = new LinkedHashMap<>();

        shipmentFlight.put("airline", "LH");
        shipmentFlight.put("flightNumber", "1023243");
        shipmentFlight.put("departureAirport", "FRAA");
        shipmentFlight.put("destinationAirport", "STRR");
        shipmentFlight.put("departureTime", "2015-06-02T21:34:33.616Z");
        shipmentFlight.put("destinationTime", "2015-06-02T21:34:33.616Z");
        shipmentFlight.put("price", FLIGHTPRICE);

        flight.put("shipmentFlight", shipmentFlight);

        return flight;
    }

    // Create a Shipment without Cargo informations.
    private Map<String, Object>  createIncompleteShipmentResourceHashMap() throws Exception {
        String uuidSender = createCustomer("Herbert Hollig");
        String uuidReceiver = createCustomer("Herbert Hollig");
        Map<String, Object> shipment = new LinkedHashMap<>();
        shipment.put("uuidSender", uuidSender);
        shipment.put("uuidReceiver", uuidReceiver);
        shipment.put("customerTypeEnum", "RECEIVER");

        Map<String, Object> cargo = new LinkedHashMap<>();
        cargo.put("numberPackages", "");
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

        Map<String, Object> flight = new LinkedHashMap<>();
        flight.put("flightNumber", "10243");
        flight.put("airline", "LH");
        flight.put("departureAirport", "FRA");
        flight.put("destinationAirport", "STR");
        flight.put("departureTime", "2015-06-02T21:34:33.616Z");
        flight.put("destinationTime", "2015-06-02T21:34:33.616Z");
        flight.put("price", FLIGHTPRICE);
        shipment.put("shipmentFlight", flight);

        return shipment;
    }

    private String createPathExtension(Customer customer) {
        return "/" + customer.uuid;
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private Map<String, Object> createInvoiceResourceHashMap() throws Exception {

        Map<String, Object> invoice = new LinkedHashMap<>();
        invoice.put("invoiceCreationDate", "2015-06-02T21:34:33.616Z");
        invoice.put("preCarriage", 40.00);
        invoice.put("exportInsurance", 100.00);
        invoice.put("exportCustomsClearance", 200.00);
        invoice.put("flightPrice", 300.00);
        invoice.put("importInsurance", 110.00);
        invoice.put("importCustomsClearance", 210.00);
        invoice.put("onCarriage", 400.00);
        invoice.put("managementFee", 50.00);
        invoice.put("serviceFee", 60.00);
        invoice.put("discount", 20.00);

        return invoice;

    }
}
