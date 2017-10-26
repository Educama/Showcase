package org.educama.shipment.api.datastructure;

import java.util.Date;

/**
 * Re-usable data structure used by resources.
 */
public class ShipmentHistoricTaskInstanceDS {

    public String asignee;
    public String deleteReason;
    public String description;
    public Date dueDate;
    public Date endTime;
    public Date followUpDate;
    public String name;
    public String owner;
    public String parentId;
    public int priority;
    public Date startTime;
    public String definitionKey;
    public String trackingId;

    public ShipmentHistoricTaskInstanceDS(String asignee, String deleteReason, String description, Date dueDate,
            Date endTime, Date followUpDate, String name, String owner, String parentId, int priority,
            Date startTime, String definitionKey, String trackingId) {
        super();
        this.asignee = asignee;
        this.deleteReason = deleteReason;
        this.description = description;
        this.dueDate = dueDate;
        this.endTime = endTime;
        this.followUpDate = followUpDate;
        this.name = name;
        this.owner = owner;
        this.parentId = parentId;
        this.priority = priority;
        this.startTime = startTime;
        this.definitionKey = definitionKey;
        this.trackingId = trackingId;
    }

}
