package org.educama.shipment.boundary;

import java.util.List;
import org.educama.shipment.boundary.datastructure.ShipmentTaskDS;

public interface ShipmentTaskBoundaryService {
	/**
     * Retrieves all tasks.
     * 
     * @return a collection of all tasks
     */
	List<ShipmentTaskDS> findAll();
}
