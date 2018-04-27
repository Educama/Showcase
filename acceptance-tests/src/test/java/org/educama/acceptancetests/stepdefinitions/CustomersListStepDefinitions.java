package org.educama.acceptancetests.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.educama.acceptancetests.steps.CustomersListSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Step definitions for the shipment list.
 */
public class CustomersListStepDefinitions extends GlobalStepDefinitions {

    @Steps
    CustomersListSteps user;

    @Given("I start to create a new customer")
    public void givenIStartToCreateANewCustomer() {
        user.openCustomersListPage();
    }

    @When("I abort the adding of a new customer with $customerName as customer name, $street as street, $houseNumber as house number, $zipCode as zip code and $city as city")
    public void iAbortTheAddingOfANewCustomer(String customerName, String street, String houseNumber, String zipCode, String city) {
        user.addNewCustomer(customerName, street, houseNumber, zipCode, city, false);
    }

    @Then("There arent new customer data in the system")
    public void thereArentNewCustomerDataInTheSystem() {
        user.checksTheCustomersListData(false);
    }

    @Given("There is a customer named Apple")
    public void thereIsACustomerNamedApple() {
        user.openCustomersListPage();
        if (!user.checksTheCustomerListDataForApple()) {
            user.addNewCustomer("Apple", "default", "default", "default", "default", true);
        }
    }

    @When("I change the name $customerName to $changeName and save the data")
    public void iChangeTheNameAppleToSamsungAndSaveTheData(String customerName, String changeName) {
        user.openCustomerChangeView(customerName);
        user.changeCustomerData(changeName, true);
    }

    @Then("The name of the customer is $customerName")
    public void theNameOfTheCustomerIsSamsung(String customerName) {
        user.checksTheCustomerListDataForChangedAppleToSamsung(true);
        user.openCustomerChangeView(customerName);
        user.changeCustomerData("Apple", true);
    }

    @When("I change the name $customerName to $changeName and abort the action")
    public void iChangeTheNameAppleToSamsungAndAbortTheAction(String customerName, String changeName) {
        user.openCustomerChangeView(customerName);
        user.changeCustomerData(changeName, false);
    }

    @Then("There arent any changes in the system")
    public void theraArentAnyChangesInTheSystem() {
        user.checksTheCustomerListDataForChangedAppleToSamsung(false);
    }
}
