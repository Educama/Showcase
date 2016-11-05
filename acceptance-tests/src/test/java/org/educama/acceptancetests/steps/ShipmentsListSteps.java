package org.educama.acceptancetests.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.educama.acceptancetests.pages.ShipmentsListPage;

import net.thucydides.core.annotations.Step;

public class ShipmentsListSteps {

	ShipmentsListPage shipmentsListPage;

	@Step
	public void opensThePage() {
		shipmentsListPage.open();
		shipmentsListPage.openShipments();
	}

	@Step
	public void openShipmentsListPage() {
		shipmentsListPage.openShipments();
	}

	@Step
	public void checksTheShipmentsList() {
		assertThat("There are cases in the case list.", shipmentsListPage.getShipmentsList().size() == 0, is(true));
	}

	@Step
	public void checksTheShipmentListForOneShipment() {
		List<String> shipmentsList = shipmentsListPage.getShipmentsList();
		int size = shipmentsList.size();

		assertThat("There are" + size + " shipment/s in the shipment list", size == 1, is(true));
	}

	@Step
	public void addOneShipment() {
		shipmentsListPage.addShipment();
	}

	@Step
	public void openAddShipmentModal() {
		shipmentsListPage.openAddShipmentModal();
	}

	@Step
	public void closeAddShipmentModal() {
		shipmentsListPage.closeAddShipmentModal();
	}

}
