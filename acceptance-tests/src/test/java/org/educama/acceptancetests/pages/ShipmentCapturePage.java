package org.educama.acceptancetests.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Page (see "Page Object Pattern") for the shipment capture.
 */
@DefaultUrl("http://localhost:8090/shipments/capture")
public class ShipmentCapturePage extends PageObject {

    @FindBy(id = "shipment-capture-component_sender")
    WebElement formFieldSender;

    @FindBy(id = "shipment-capture-component_receiver")
    WebElement formFieldReceiver;

    @FindBy(id = "shipment-capture-component_cancel-button")
    WebElement cancelButton;

    @FindBy(id = "shipment-capture-component_create-button")
    WebElement createButton;

    public void createShipment() {

        formFieldSender.sendKeys("Apple");
        $("//ul//li[contains(.,\"Apple\")]").waitUntilVisible();
        formFieldSender.sendKeys(Keys.DOWN);
        formFieldSender.sendKeys(Keys.RETURN);

        formFieldReceiver.sendKeys("NovaTec Consulting GmbH");
        $("//ul//li[contains(.,\"NovaTec Consulting GmbH\")]").waitUntilVisible();
        formFieldReceiver.sendKeys(Keys.DOWN);
        formFieldReceiver.sendKeys(Keys.RETURN);

        clickOn(createButton);
    }
}
