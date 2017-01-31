package org.educama.shipment.api.resource;

import java.util.Date;
import org.educama.shipment.boundary.datastructure.ShipmentTaskDS;

public class ShipmentTaskResource {
	
	public Date createTime;
	public String trackingId;
    public String taskId;
    public String name;
    public String description;
    public String assignee;
    public String customer;
    
    public ShipmentTaskResource fromTask(ShipmentTaskDS shipmentTask) {
    	this.createTime = shipmentTask.createTime;
    	this.trackingId = shipmentTask.trackingId;
    	this.taskId = shipmentTask.taskId;
    	this.name = shipmentTask.name;
    	this.description = shipmentTask.description;
        this.assignee = shipmentTask.assignee;
        this.customer=shipmentTask.customer;
        return this;
    }
}
