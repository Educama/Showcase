package org.educama.shipment.boundary;

import org.educama.shipment.model.Shipment;

import java.util.Collection;

public interface ShipmentBoundaryService {

    Shipment createShipment(Shipment shipment);
    Collection<Shipment> findAll();
}
