package org.educama.shipment.api.resource;

import org.educama.shipment.model.Shipment;

import java.util.ArrayList;
import java.util.Collection;

public class ShipmentListResource {

    public Collection<ShipmentResource> shipments;

    public ShipmentListResource() {

        this.shipments = new ArrayList<>();
    }

    public ShipmentListResource(Collection<Shipment> shipmentsList) {

        this.shipments = new ArrayList<>();
        for (Shipment currentShipment : shipmentsList) {

            shipments.add(new ShipmentResource(currentShipment));
        }
    }
}
