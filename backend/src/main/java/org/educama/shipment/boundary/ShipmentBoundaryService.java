package org.educama.shipment.boundary;

import org.educama.shipment.model.Shipment;

import java.util.Collection;

/**
 * Boundary service for shipment.
 */
public interface ShipmentBoundaryService {

    /**
     * Creates a shipment.
     *
     * @param shipment which is to be created as Case
     * @return the created shipment
     */
    Shipment createShipment(Shipment shipment);

    /**
     * Retrieves all shipments.
     *
     * @return a collection of all shipments
     */
    Collection<Shipment> findAll();
}
