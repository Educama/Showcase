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
        final int shipments = 0;
        user.checksTheShipmentsList(shipments);
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
        final int shipments = 1;
        user.checksTheShipmentsList(shipments);
    }

    /*
     * Caselist with 1 shipment - Data check
     */
    @Given("There is a shipment")
    public void givenThereIsAShipment() {
        user.openShipmentsListPage();
    }

    @Then("I see Apple as sender and NovaTech as receiver")
    public void thenISeeAppleAsSenderAndNovaTechAsReceiver() {
        user.checksTheShipmentsListData();
    }

    /*
     * Caselist with five cases
     */
    @Given("There are 5 shipments")
    public void givenThereAre5Shipments() {
        user.openShipmentsListPage();
        //final int toAdd = 4;
        //for (int i = 0; i < toAdd; i++) {
        user.addfourShipments();
        //}
    }

    @Then("I see 5 shipments")
    public void thenISeeFiveShipments() {
        final int shipments = 5;
        user.checksTheShipmentsList(shipments);
    }

    /*
     * Caselist with 5 shipments - Data check
     */
    @Given("There are five shipments")
    public void givenThereAreFiveShipments() {
        user.openShipmentsListPage();
    }

    @Then("I see the expected senders and receivers")
    public void thenISeeTheExpectedSendersAndReceivers() {
        user.checksTheShipmentsListDataForFive();
    }
}
