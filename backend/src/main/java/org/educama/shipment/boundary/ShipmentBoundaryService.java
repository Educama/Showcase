package org.educama.shipment.boundary;

import org.educama.shipment.api.resource.ShipmentResource;
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

    /**
     * Retrieves one shipment.
     *
     * @param trackingId to get required shipment
     * @return returns the shipment as a resource
     */
    ShipmentResource getShipment(String trackingId);

    /**
     * Retrieves one shipment and updates it.
     *
     * @param trackingId to get required shipment
     * @return returns the  updated shipment as a resource
     */
    ShipmentResource updateShipment(String trackingId, Shipment saveShipmentResource);
}
