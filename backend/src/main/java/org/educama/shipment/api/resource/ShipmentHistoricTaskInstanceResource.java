package org.educama.shipment.api.resource;

import java.util.Date;

import org.educama.shipment.api.datastructure.ShipmentHistoricTaskInstanceDS;

/**
 * REST-Resource for list of historic (completed, suspended) tasks.
 */
public class ShipmentHistoricTaskInstanceResource {


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

    public ShipmentHistoricTaskInstanceResource fromHistoricTaskInstance(ShipmentHistoricTaskInstanceDS historicTaskInstanceDS) {
        this.asignee = historicTaskInstanceDS.asignee;
        this.deleteReason = historicTaskInstanceDS.deleteReason;
        this.description = historicTaskInstanceDS.description;
        this.dueDate = historicTaskInstanceDS.dueDate;
        this.endTime = historicTaskInstanceDS.endTime;
        this.followUpDate = historicTaskInstanceDS.followUpDate;
        this.name = historicTaskInstanceDS.name;
        this.owner = historicTaskInstanceDS.owner;
        this.parentId = historicTaskInstanceDS.parentId;
        this.priority = historicTaskInstanceDS.priority;
        this.startTime = historicTaskInstanceDS.startTime;
        this.definitionKey = historicTaskInstanceDS.definitionKey;
        this.trackingId = historicTaskInstanceDS.trackingId;

        return this;
    }
}
