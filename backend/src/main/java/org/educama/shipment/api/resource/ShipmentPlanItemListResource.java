package org.educama.shipment.api.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.educama.shipment.api.datastructure.ShipmentPlanItemDS;

/**
 * REST-Resource for list of shipment plan items.
 */
public class ShipmentPlanItemListResource {

    public Collection<ShipmentPlanItemResource> plantItems;

    public ShipmentPlanItemListResource fromPlanItemCollection(List<ShipmentPlanItemDS> planItemList) {
        this.plantItems = new ArrayList<>();
        for (ShipmentPlanItemDS currentPlantItem : planItemList) {
            plantItems.add(new ShipmentPlanItemResource().fromShipmentPlanItem(currentPlantItem));
        }
        return this;
    }

}
