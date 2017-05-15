package org.educama.shipment.api.resource;

import org.educama.shipment.model.Shipment;

import java.util.ArrayList;
import java.util.Collection;

/**
 * REST-Resource for a list of shipments.
 */
public class ShipmentListResource {

    public Collection<ShipmentResource> shipments;

    public ShipmentListResource fromShipmentCollection(Collection<Shipment> shipmentsList) {
        this.shipments = new ArrayList<>();

        for (Shipment currentShipment : shipmentsList) {
            shipments.add(new ShipmentResource().fromShipment(currentShipment));
        }

        return this;
    }
}
