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
 * Page (see "Page Object Pattern") for the list of customers.
 */
@DefaultUrl("http://localhost:8090/customers")
public class CustomerListPage extends PageObject {

    @FindBy(id = "customer-list-component_customer-table")
    WebElement listOfCustomers;

    @FindBy(id = "customer-list-page_refresh-button")
    WebElement refreshButton;

    @FindBy(id = "customer-list-page_new-button")
    WebElement newButton;

    @FindBy(id = "customer-capture-component_customer-name")
    WebElement formFieldCustomerName;

    @FindBy(id = "customer-capture-component_save-button")
    WebElement saveButton;

    @FindBy(id = "customer-capture-component_cancel-button")
    WebElement cancelButton;

    @FindBy(xpath = "//span[@class='ui-cell-data'][text()='Apple']")
    WebElement customerApple;

    @FindBy(xpath = "//span[@class='ui-cell-data'][text()='Samsung']")
    WebElement customerSamsung;

    final int waitTime = 2000;

    public List<String> getCustomersList(int columnId) {
        waitABit(waitTime);
        // Now get all the TR elements from the table
        List<WebElement> allRows = listOfCustomers.findElements(By.tagName("tr"));
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

    public void openCustomerCapturePage() {
        clickOn(newButton);
    }

    public void refreshCustomerList() {
        clickOn(refreshButton);
    }

    public void openCustomerChangePage(String customerName) {
        if (customerName.equals("Apple")) {
            clickOn(customerApple);
        }
        if (customerName.equals("Samsung")) {
            clickOn(customerSamsung);
        }
    }

    public void changeCustomerName(String customerName, boolean save) {
        formFieldCustomerName.clear();
        formFieldCustomerName.sendKeys(customerName);
        if (save) {
            clickOn(saveButton);
        } else {
            clickOn(cancelButton);
        }
        waitABit(waitTime);
    }
}
