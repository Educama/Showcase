package org.educama.acceptancetests.stepdefinitions;

import org.educama.acceptancetests.steps.ShipmentsListSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import net.thucydides.core.annotations.Steps;

public class ShipmentsListStepDefinitions extends GlobalStepDefinitions {

	@Steps
	ShipmentsListSteps user;

	/*
	 * Empty Caselist
	 */

	@Given("There are no open shipments")
	public void givenThereAreNoOpenShipments() {
		user.opensThePage();
		user.openShipmentsListPage();
	}

	@When("I show the list of current shipments")
	public void whenIShowTheListOfOpenShipments() {

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
		user.opensThePage();
		user.openShipmentsListPage();
		user.openAddShipmentModal();
		user.addOneShipment();
		user.closeAddShipmentModal();
	}

	@Then("I see 1 shipment")
	public void thenISeeOneShipment() {
		user.checksTheShipmentListForOneShipment();
	}
}
