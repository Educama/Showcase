package org.educama.acceptancetests.steps;

import net.thucydides.core.annotations.Step;
import java.util.List;
import org.educama.acceptancetests.pages.ShipmentCapturePage;
import org.educama.acceptancetests.pages.ShipmentListPage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Steps for shipment list.
 */
public class ShipmentsListSteps {

    ShipmentListPage shipmentsListPage;
    ShipmentCapturePage shipmentCapturePage;

    @Step
    public void openShipmentsListPage() {
        shipmentsListPage.open();
    }

    @Step
    public void checksTheShipmentsList(int shipments) {
        List<String> shipmentsList = shipmentsListPage.getShipmentsList(0);
        int size = shipmentsList.size();
        assertThat("There are " + shipments + " shipment/s in the shipment list", size == shipments, is(true));
    }

    @Step
    public void checksTheShipmentsListData() {
        String sender = "Apple Street 1, 12345 Silicon Valley";
        String receiver = "Dieselstrasse 18/1, 70771 Leinfelden-Echterdingen";
        List<String> shipmentsListSender = shipmentsListPage.getShipmentsList(1);
        List<String> shipmentsListReceiver = shipmentsListPage.getShipmentsList(2);
        assertThat("The sender is " + sender, sender.equals(shipmentsListSender.get(0)), is(true));
        assertThat("The receiver is " + receiver, receiver.equals(shipmentsListReceiver.get(0)), is(true));
    }

    @Step
    public void checksTheShipmentsListDataForFive() {
        final int testNumberThree = 3;
        final int testNumberFour = 4;

        String sender1 = "Apple Street 1, 12345 Silicon Valley";
        String receiver1 = "Dieselstrasse 18/1, 70771 Leinfelden-Echterdingen";
        String sender2 = "Apple Street 1, 12345 Silicon Valley";
        String receiver2 = "Epplestraße 225, 70567 Stuttgart";
        String sender3 = "Dieselstrasse 18/1, 70771 Leinfelden-Echterdingen";
        String receiver3 = "Apple Street 1, 12345 Silicon Valley";
        String sender4 = "Epplestraße 225, 70567 Stuttgart";
        String receiver4 = "Dieselstrasse 18/1, 70771 Leinfelden-Echterdingen";
        String sender5 = "Dieselstrasse 18/1, 70771 Leinfelden-Echterdingen";
        String receiver5 = "Epplestraße 225, 70567 Stuttgart";
        List<String> shipmentsListSender = shipmentsListPage.getShipmentsList(1);
        List<String> shipmentsListReceiver = shipmentsListPage.getShipmentsList(2);
        assertThat("The sender is " + sender1, sender1.equals(shipmentsListSender.get(0)), is(true));
        assertThat("The receiver is " + receiver1, receiver1.equals(shipmentsListReceiver.get(0)), is(true));
        assertThat("The sender is " + sender2, sender2.equals(shipmentsListSender.get(1)), is(true));
        assertThat("The receiver is " + receiver2, receiver2.equals(shipmentsListReceiver.get(1)), is(true));
        assertThat("The sender is " + sender3, sender3.equals(shipmentsListSender.get(2)), is(true));
        assertThat("The receiver is " + receiver3, receiver3.equals(shipmentsListReceiver.get(2)), is(true));
        assertThat("The sender is " + sender4, sender4.equals(shipmentsListSender.get(testNumberThree)), is(true));
        assertThat("The receiver is " + receiver4, receiver4.equals(shipmentsListReceiver.get(testNumberThree)), is(true));
        assertThat("The sender is " + sender5, sender5.equals(shipmentsListSender.get(testNumberFour)), is(true));
        assertThat("The receiver is " + receiver5, receiver5.equals(shipmentsListReceiver.get(testNumberFour)), is(true));
    }

    @Step
    public void addOneShipment() {
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment("Apple", "NovaTec Consulting GmbH");
    }

    @Step
    public void addfourShipments() {
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment("Apple", "Daimler AG");
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment("NovaTec Consulting GmbH", "Apple");
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment("Daimler AG", "NovaTec Consulting GmbH");
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment("NovaTec Consulting GmbH", "Daimler AG");
    }
}
