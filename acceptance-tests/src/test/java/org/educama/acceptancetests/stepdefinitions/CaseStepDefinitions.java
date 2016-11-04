package org.educama.acceptancetests.stepdefinitions;

import org.educama.acceptancetests.steps.CaseSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import net.thucydides.core.annotations.Steps;

public class CaseStepDefinitions extends GlobalStepDefinitions {

	@Steps
	CaseSteps user;

	/*
	 * Empty Caselist
	 */

	@Given("There are no open cases")
	public void givenThereAreNoOpenCases() {
		user.opensThePage();
		user.openCasePage();
	}

	@When("I show the list of open cases")
	public void whenIShowTheListOfOpenCases() {

	}

	@Then("I see an empty list")
	public void thenISeeAnEmptyList() {
		user.checksTheCaseList();
	}

	/*
	 * Caselist with one case
	 */
	@Given("There is 1 case")
	public void givenThereIsOneCase() {
		user.opensThePage();
		user.openCasePage();
		user.openAddCaseModal();
		user.addOneCase();
		user.closeAddCaseModal();
	}

	@Then("I see 1 case")
	public void thenISeeOneCase() {
		user.checksTheCaseListForOneCase();
	}
}
