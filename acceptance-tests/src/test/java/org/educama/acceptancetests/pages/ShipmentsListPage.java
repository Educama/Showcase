package org.educama.acceptancetests.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("http://localhost:8090")
public class ShipmentsListPage extends PageObject {

	@FindBy(id = "casePage")
	WebElement pageCase;

	@FindBy(id = "casesList")
	WebElement tableCase;

	@FindBy(id = "addCase")
	WebElement buttonAddCase;

	@FindBy(id = "transportType")
	WebElement inputTransportType;

	@FindBy(id = "customerName")
	WebElement inputCustomerName;

	@FindBy(id = "newCase")
	WebElement buttonNewCase;

	public void openShipments() {
		clickOn(pageCase);
	}

	public List<String> getShipmentsList() {
		List<String> shipmentsList = new ArrayList<String>();
		// Now get all the TR elements from the table
		List<WebElement> allRows = tableCase.findElements(By.tagName("tr"));
		// And iterate over them, getting the cells
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			// Is a case in this TR?
			int i = 0;
			for (WebElement cell : cells) {
				// Count only one row for each case
				if (i == 0) {
					shipmentsList.add(cell.getText());
				}
				i++;
			}
		}
		return shipmentsList;
	}

	public void addShipment() {
		inputTransportType.sendKeys("Sperrguttransport");
		inputCustomerName.sendKeys("John Doe");

	}

	public void openAddShipmentModal() {
		clickOn(buttonAddCase);
	}

	public void closeAddShipmentModal() {
		clickOn(buttonNewCase);
	}
}
