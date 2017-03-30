package org.educama.shipment.boundary.impl;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.task.Task;
import org.educama.shipment.api.datastructure.ShipmentTaskDS;
import org.educama.shipment.boundary.ShipmentTaskBoundaryService;
import org.educama.shipment.model.Shipment;
import org.educama.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Boundary service implementation for shipment tasks.
 */
@Service
@Transactional(readOnly = true)
public class ShipmentTaskBoundaryServiceImpl implements ShipmentTaskBoundaryService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CaseService caseService;

    @Override
    public List <ShipmentTaskDS> findAll() {
        Collection <Task> tasks = taskService.createTaskQuery().taskAssignee("educama").list();
        List <ShipmentTaskDS> shipmentTasks = new ArrayList<ShipmentTaskDS>();
        for (Task task : tasks) {
            CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceId(task.getCaseInstanceId()).singleResult();
            Shipment shipment = shipmentRepository.findOneBytrackingId(caseInstance.getBusinessKey());
            ShipmentTaskDS shipmentTaskDS = new ShipmentTaskDS(task.getCreateTime(), shipment.trackingId, task.getId(), task.getName(), task.getDescription(), task.getAssignee(), shipment.sender.name);
            shipmentTasks.add(shipmentTaskDS);
        }
        return shipmentTasks;
    }
}
