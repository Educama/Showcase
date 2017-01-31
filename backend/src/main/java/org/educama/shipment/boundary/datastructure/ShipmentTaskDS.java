package org.educama.shipment.boundary.datastructure;

import java.util.Date;

public class ShipmentTaskDS {
	
	public Date createTime;
	public String trackingId;
    public String taskId;
    public String name;
    public String description;
    public String assignee;
    public String customer;
    
    public ShipmentTaskDS(Date createTime, String trackingId2, String taskId, String name, String description,
			String assignee, String customer) {
		super();
		this.createTime = createTime;
		this.trackingId = trackingId2;
		this.taskId = taskId;
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.customer = customer;
	}
}
