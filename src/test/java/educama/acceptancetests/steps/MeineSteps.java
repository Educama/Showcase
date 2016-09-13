package educama.acceptancetests.steps;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import educama.acceptancetests.pages.WelcomePage;
import net.thucydides.core.annotations.Step;

public class MeineSteps {

	WelcomePage welcomePage;
	
	@Step
	public void opensThePage() {
		welcomePage.open();
	}

	@Step
	public void entersTheName(String name) {
		welcomePage.enterNameInInputbox(name);
	}

	@Step
	public void checksHeading(String name) {
		assertThat("The greetings are wrong", welcomePage.getGreetings().contains(name), is(true));
	}
}
