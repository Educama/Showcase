package org.educama.shipment.boundary;

import org.educama.shipment.api.datastructure.ShipmentTaskDS;

import java.util.List;


/**
 * Boundary service for shipment tasks.
 */
public interface ShipmentTaskBoundaryService {
    /**
     * Retrieves all activeTasks.
     *
     * @return a collection of all active tasks
     */
     List<ShipmentTaskDS> findAllActive();

    /**
     * Retrieves all enabled tasks.
     *
     * @return a collection of all activeTasks
     */
    List<ShipmentTaskDS> findAllEnabled();
}
