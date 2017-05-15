package org.educama.acceptancetests.steps;

import net.thucydides.core.annotations.Step;
import org.educama.acceptancetests.pages.ShipmentCapturePage;
import org.educama.acceptancetests.pages.ShipmentListPage;

import java.util.List;

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
    public void checksTheShipmentsList() {
        assertThat("There are shipments in the shipment list.", shipmentsListPage.getShipmentsList().size() == 0, is(true));
    }

    @Step
    public void checksTheShipmentListForOneShipment() {
        List<String> shipmentsList = shipmentsListPage.getShipmentsList();
        int size = shipmentsList.size();
        assertThat("There are " + size + " shipment/s in the shipment list", size == 1, is(true));
    }

    @Step
    public void addOneShipment() {
        shipmentsListPage.openShipmentCapturePage();
        shipmentCapturePage.createShipment();
    }
}
