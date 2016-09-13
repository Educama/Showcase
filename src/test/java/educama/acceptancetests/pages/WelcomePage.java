package educama.acceptancetests.pages;

import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("http://localhost:8081")
public class WelcomePage extends PageObject {

	@FindBy(id = "nameInputBox")
	WebElement inputName;
	
	@FindBy(id = "greetingsHeading")
	WebElement headingGreetings;
	
	public void enterNameInInputbox(String name) {
		inputName.sendKeys(name);
	}

	public String getGreetings() {
		return headingGreetings.getText();
	}
}
