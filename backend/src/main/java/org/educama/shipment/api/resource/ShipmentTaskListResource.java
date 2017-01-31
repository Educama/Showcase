package org.educama.shipment.api.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.educama.shipment.boundary.datastructure.ShipmentTaskDS;

public class ShipmentTaskListResource {

    public Collection<ShipmentTaskResource> tasks;

    public ShipmentTaskListResource fromTaskCollection(List<ShipmentTaskDS> taskList) {
        this.tasks = new ArrayList<>();
        for (ShipmentTaskDS currentTask : taskList) {
            tasks.add(new ShipmentTaskResource().fromTask(currentTask));
        }
        return this;
    }
}
