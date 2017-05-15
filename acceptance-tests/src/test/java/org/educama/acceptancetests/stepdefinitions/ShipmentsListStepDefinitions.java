package org.educama.acceptancetests.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.educama.acceptancetests.steps.ShipmentsListSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Step definitions for the shipment list.
 */
public class ShipmentsListStepDefinitions extends GlobalStepDefinitions {

    @Steps
    ShipmentsListSteps user;

    /*
     * Empty Caselist
     */
    @Given("There are no open shipments")
    public void givenThereAreNoOpenShipments() {
        user.openShipmentsListPage();
    }

    @When("I show the list of current shipments")
    public void whenIShowTheListOfOpenShipments() {
        // nothing to do
    }

    @Then("I see an empty list")
    public void thenISeeAnEmptyList() {
        user.checksTheShipmentsList();
    }

    /*
     * Caselist with one case
     */
    @Given("There is 1 shipment")
    public void givenThereIsOneShipment() {
        user.openShipmentsListPage();
        user.addOneShipment();
    }

    @Then("I see 1 shipment")
    public void thenISeeOneShipment() {
        user.checksTheShipmentListForOneShipment();
    }
}
