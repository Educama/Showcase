package educama.acceptancetests.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import educama.acceptancetests.pages.WelcomePage;
import educama.acceptancetests.steps.MeineSteps;
import net.thucydides.core.annotations.Steps;

public class GlobalStepDefinitions {

	WelcomePage welcomePage;
	
	@Steps
	MeineSteps user;
	
	@Given("There are no open cases")
	@Pending
	public void givenThereAreNoOpenCases() {
		user.opensThePage();
	}
	
	@When("I show the list of open cases")
	@Pending
	public void whenIShowTheListOfOpenCases() {
		
	}
	
	/*
	 * Show-cases.story
	 */
	
	@Given("the main page is shown")
	public void givenTheMainPageIsShown() {
		user.opensThePage();
	}
	
	@When("I enter the name $name")
	public void whenIEnterTheName(@Named("name") String name) {
		user.entersTheName(name);
	}
	
	@Then("$name should be greeted")
	public void thenNameShouldBeGreeted(@Named("name") String  name) {
		user.checksHeading(name);
	}
}
