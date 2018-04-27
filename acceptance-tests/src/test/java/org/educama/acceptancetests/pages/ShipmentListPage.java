package org.educama.acceptancetests.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Page (see "Page Object Pattern") for the list of shipments.
 */
@DefaultUrl("http://localhost:8090/shipments")
public class ShipmentListPage extends PageObject {

    @FindBy(id = "shipment-list-component_shipments-table")
    WebElement listOfShipments;

    @FindBy(id = "shipment-list-page_refresh-button")
    WebElement refreshButton;

    @FindBy(id = "shipment-list-page_new-button")
    WebElement newButton;

    public List<String> getShipmentsList(int columnId) {
        // Now get all the TR elements from the table
        List<WebElement> allRows = listOfShipments.findElements(By.tagName("tr"));
        // And iterate over them, getting the cells
        return allRows.stream()
                .map(row -> {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    // row must have more than one column. Otherwise it is the "No records found." message.
                    return cells.size() > 1 ? cells.get(columnId).getText() : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void openShipmentCapturePage() {
        clickOn(newButton);
    }

    public void refreshShipmentList() {
        clickOn(refreshButton);
    }
}
