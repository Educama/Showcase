package org.educama.shipment.api.resource;

import java.util.ArrayList;
import java.util.Collection;

public class TaskListResource {

    public Collection<TaskResource> tasks;

    public TaskListResource fromTaskCollection(Collection<org.camunda.bpm.engine.task.Task> taskList) {
        this.tasks = new ArrayList<>();

        for (org.camunda.bpm.engine.task.Task currentTask : taskList) {
            tasks.add(new TaskResource().fromTask(currentTask));
        }
        return this;
    }
}
