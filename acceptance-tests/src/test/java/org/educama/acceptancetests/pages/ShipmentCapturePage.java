package org.educama.acceptancetests.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

/**
 * Page (see "Page Object Pattern") for the shipment capture.
 */
@DefaultUrl("http://localhost:8090/shipments/capture")
public class ShipmentCapturePage extends PageObject {

    @FindBy(id = "shipment-capture-component_customer")
    WebElement formFieldCustomer;

    @FindBy(id = "shipment-capture-component_sender-address")
    WebElement formFieldSenderAddress;

    @FindBy(id = "shipment-capture-component_receiver-address")
    WebElement formFieldReceiverAddress;

    @FindBy(id = "shipment-capture-component_cancel-button")
    WebElement cancelButton;

    @FindBy(id = "shipment-capture-component_create-button")
    WebElement createButton;

    public void createShipment() {
        formFieldCustomer.sendKeys("John Doe");
        formFieldSenderAddress.sendKeys("Dummy Street, 12345 Dummy Sender Town");
        formFieldReceiverAddress.sendKeys("Dummy Street, 12345 Dummy Receiver Town");
        clickOn(createButton);
    }
}
