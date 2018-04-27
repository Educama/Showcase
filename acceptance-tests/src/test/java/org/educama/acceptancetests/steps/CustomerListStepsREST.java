package org.educama.acceptancetests.steps;

import com.jayway.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.educama.customer.model.Address;
import org.educama.customer.model.Customer;
import org.junit.Assert;

/**
 * List Steps to create Customer.
 */
public class CustomerListStepsREST {

    private static final String RESTURI = "http://localhost:8091/educama/v1/customers";
    private static final int EXPECTEDCREATEDSTATUS = 201;
    private static final int EXPECTEDOKSTATUS = 200;
    private Customer customer;
    private String uuid;

    @Step
    public void addNewCustomer(String customerName, String street, String houseNumber, String zipCode, String city) {

        customer = new Customer(customerName, new Address(street, houseNumber, zipCode, city));

        Response responsePost = SerenityRest.given().contentType("application/json")
                .content(customer).and().expect().that().statusCode(EXPECTEDCREATEDSTATUS)
                .when().post(RESTURI).andReturn();

        uuid = responsePost.body().jsonPath().get("uuid");
        responsePost.body().prettyPrint();
    }

    @Step
    public void checksTheCustomersListData() {

        String getCustomerByUuid = RESTURI + "/" + uuid;

        Response responseGet = SerenityRest.expect().that().statusCode(EXPECTEDOKSTATUS)
                .get(getCustomerByUuid).andReturn();

        String customerName = responseGet.body().jsonPath().get("name");
        String street = responseGet.body().jsonPath().get("address.street");
        String streetNo = responseGet.body().jsonPath().get("address.streetNo");
        String zipCode = responseGet.body().jsonPath().get("address.zipCode");
        String city = responseGet.body().jsonPath().get("address.city");

        Assert.assertEquals(customer.name, customerName);
        Assert.assertEquals(customer.address.street, street);
        Assert.assertEquals(customer.address.streetNo, streetNo);
        Assert.assertEquals(customer.address.zipCode, zipCode);
        Assert.assertEquals(customer.address.city, city);
    }
}
