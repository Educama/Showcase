package org.educama.shipment.api.resource;

import org.educama.shipment.api.datastructure.ShipmentPlanItemDS;

/**
 * REST-Resource for list of shipment plan items.
 */
public class ShipmentPlanItemResource {
    public String description;
    public String id;
    public String name;
    public String type;
    public boolean isEnabled;
    public String trackingId;


    public ShipmentPlanItemResource fromShipmentPlanItem(ShipmentPlanItemDS shipmentPlanItem) {
        this.description = shipmentPlanItem.description;
        this.id = shipmentPlanItem.id;
        this.name = shipmentPlanItem.name;
        this.type = shipmentPlanItem.type;
        this.isEnabled = shipmentPlanItem.isEnabled;
        this.trackingId = shipmentPlanItem.trackingId;

        return this;
    }
}
