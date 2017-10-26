package org.educama.shipment.api.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.educama.shipment.api.datastructure.ShipmentHistoricTaskInstanceDS;

/**
 * REST-Resource for list of historic (completed, suspended) tasks.
 */
public class ShipmentHistoricTaskInstanceListResource {
    public Collection<ShipmentHistoricTaskInstanceResource> tasks;

    public ShipmentHistoricTaskInstanceListResource fromTaskCollection(List<ShipmentHistoricTaskInstanceDS> taskList) {
        this.tasks = new ArrayList<>();
        for (ShipmentHistoricTaskInstanceDS currentTask : taskList) {
            tasks.add(new ShipmentHistoricTaskInstanceResource().fromHistoricTaskInstance(currentTask));
        }
        return this;
    }
}
