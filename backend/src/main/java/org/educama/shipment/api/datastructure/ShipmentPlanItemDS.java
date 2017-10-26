package org.educama.shipment.api.datastructure;

/**
 * Re-usable data structure used by resources.
 */
public class ShipmentPlanItemDS {

   public String description;
   public String id;
   public String name;
   public String type;
   public boolean isEnabled;
   public String trackingId;


   public ShipmentPlanItemDS(String description, String id, String name, String type, boolean isEnabled, String trackingId) {
       super();
       this.description = description;
       this.id = id;
       this.name = name;
       this.type = type;
       this.isEnabled = isEnabled;
       this.trackingId = trackingId;
   }

}
