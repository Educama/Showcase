package org.educama.shipment.api.resource;

import org.educama.shipment.api.datastructure.ShipmentTaskDS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * REST-Resource for list of shipment tasks.
 */
public class ShipmentTaskListResource {

    public Collection<ShipmentTaskResource> tasks;


    public ShipmentTaskListResource fromActiveTaskCollection(List<ShipmentTaskDS> taskList) {
        this.tasks = new ArrayList<>();
        for (ShipmentTaskDS currentTask : taskList) {
            tasks.add(new ShipmentTaskResource().fromTask(currentTask));
        }
        return this;
    }
}
