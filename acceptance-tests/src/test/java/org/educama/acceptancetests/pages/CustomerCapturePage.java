package org.educama.acceptancetests.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

/**
 * Page (see "Page Object Pattern") for the customer capture.
 */
@DefaultUrl("http://localhost:8090/customers/capture")
public class CustomerCapturePage extends PageObject {

    @FindBy(id = "customer-capture-component_customer-name")
    WebElement formFieldCustomerName;

    @FindBy(id = "customer-capture-component_address-street")
    WebElement formFieldAddressStreet;

    @FindBy(id = "customer-capture-component_address-house-number")
    WebElement formFieldAddressHouseNumber;

    @FindBy(id = "customer-capture-component_address-postal-code")
    WebElement formFieldAddressPostalCode;

    @FindBy(id = "customer-capture-component_address-city")
    WebElement formFieldAddressCity;

    @FindBy(id = "customer-capture-component_cancel-button")
    WebElement cancelButton;

    @FindBy(id = "customer-capture-component_create-button")
    WebElement createButton;

    @FindBy(xpath = "//option[@value = '10']")
    WebElement value;

    final int waitTime = 2000;

    public void createCustomer(String customerName, String street, String houseNumber, String zipCode, String city, boolean save) {
        formFieldCustomerName.sendKeys(customerName);
        formFieldAddressStreet.sendKeys(street);
        formFieldAddressHouseNumber.sendKeys(houseNumber);
        formFieldAddressPostalCode.sendKeys(zipCode);
        formFieldAddressCity.sendKeys(city);

        if (save) {
            clickOn(createButton);
        } else {
            clickOn(cancelButton);
        }
        clickOn(value);
        waitABit(waitTime);
    }
}
