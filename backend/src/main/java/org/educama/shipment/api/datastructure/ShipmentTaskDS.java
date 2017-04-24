package org.educama.shipment.api.datastructure;

import java.util.Date;

import org.educama.customer.model.Customer;

/**
 * Re-usable data structure used by resources.
 */
public class ShipmentTaskDS {

    public Date createTime;
    public String trackingId;
    public String taskId;
    public String name;
    public String description;
    public String assignee;
    public String customer;
    public Customer sender;
    public Customer receiver;

    public ShipmentTaskDS(Date createTime, String trackingId2, String taskId, String name, String description,
                          String assignee, Customer sender, Customer receiver) {
        super();
        this.createTime = createTime;
        this.trackingId = trackingId2;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.sender = sender;
        this.receiver = receiver;
    }
}
