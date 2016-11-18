package org.educama.shipment.boundary;

import org.educama.shipment.model.Shipment;

import java.util.Collection;

public interface ShipmentBoundaryService {

	/**
	 * Creating a shipment is done by storing it into a repository and create a Case.
	 * 
	 * @param shipment which is to be created as Case
	 * @return the created shipment instance which is enhanced by informations by the engine (e.g. ID)
	 */
    Shipment createShipment(Shipment shipment);
    
    /**
     * Finding all Shipments is done by looking up all Shipment-cases.
     * 
     * @return a collection of all found Shipment-cases.
     */
    Collection<Shipment> findAll();
}
