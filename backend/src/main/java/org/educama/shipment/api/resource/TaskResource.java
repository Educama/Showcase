package org.educama.shipment.api.resource;


public class TaskResource {

    public String id;
    public String name;
    public String description;
    public String assignee;

    public TaskResource fromTask(org.camunda.bpm.engine.task.Task shipmentTask) {
    	this.id = shipmentTask.getId();
    	this.name = shipmentTask.getName();
    	this.description = shipmentTask.getDescription();
        this.assignee = shipmentTask.getAssignee();
        
        return this;
    }
}
