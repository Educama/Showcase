package org.educama.shipment.boundary.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.task.Task;
import org.educama.shipment.api.datastructure.ShipmentHistoricTaskInstanceDS;
import org.educama.shipment.api.datastructure.ShipmentPlanItemDS;
import org.educama.shipment.api.datastructure.ShipmentTaskDS;
import org.educama.shipment.boundary.ShipmentTaskBoundaryService;
import org.educama.shipment.model.Shipment;
import org.educama.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private HistoryService historyService;

    @Override
    public List<ShipmentTaskDS> findAllActive() {
        Collection<Task> tasks = taskService.createTaskQuery().taskAssignee("educama").active().list();
        List<ShipmentTaskDS> shipmentTasks = new ArrayList<ShipmentTaskDS>();
        for (Task task : tasks) {
            CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceId(task.getCaseInstanceId())
                    .active().singleResult();
            Shipment shipment = shipmentRepository.findOneBytrackingId(caseInstance.getBusinessKey());
            ShipmentTaskDS shipmentTaskDS = new ShipmentTaskDS(task.getCreateTime(), shipment.trackingId, task.getId(),
                    task.getName(), task.getDescription(), task.getAssignee(), shipment.sender, shipment.receiver);
            shipmentTasks.add(shipmentTaskDS);
        }
        return shipmentTasks;
    }

    @Override
    public List<ShipmentPlanItemDS> findAllEnabled(String trackingId) {
        List<ShipmentPlanItemDS> shipmentEnabledActivities = new ArrayList<ShipmentPlanItemDS>();
        List<CaseExecution> enabledCaseExecutionsList = caseService.createCaseExecutionQuery().enabled().list();
        for (CaseExecution caseExecution : enabledCaseExecutionsList) {
            // returns the case instance
            CaseInstance caseInstance = caseService.createCaseInstanceQuery()
                    .caseInstanceId(caseExecution.getCaseInstanceId()).singleResult();
            if (caseInstance.getBusinessKey().equals(trackingId)) {
                // returns the shipment for this caseinstance
                Shipment shipment = shipmentRepository.findOneBytrackingId(caseInstance.getBusinessKey());
                ShipmentPlanItemDS shipmentPlanItemDS = new ShipmentPlanItemDS(caseExecution.getActivityDescription(),
                        caseExecution.getActivityId(), caseExecution.getActivityName(), caseExecution.getActivityType(),
                        caseExecution.isEnabled(), shipment.trackingId);
                shipmentEnabledActivities.add(shipmentPlanItemDS);
            }
        }
        return shipmentEnabledActivities;
    }

    @Override
    public List<ShipmentHistoricTaskInstanceDS> findAllCompleted(String trackingId) {
        List<ShipmentHistoricTaskInstanceDS> completedHistoricTaskInstancesList = new ArrayList<ShipmentHistoricTaskInstanceDS>();
        CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceBusinessKey(trackingId)
                .singleResult();
        Shipment shipment = shipmentRepository.findOneBytrackingId(trackingId);

        List<HistoricTaskInstance> historicTaskInstancesList = historyService.createHistoricTaskInstanceQuery()
                .caseInstanceId(caseInstance.getId()).taskDeleteReason("completed").list();

        for (HistoricTaskInstance task : historicTaskInstancesList) {
            ShipmentHistoricTaskInstanceDS historicInstanceTask = new ShipmentHistoricTaskInstanceDS(task.getAssignee(),
                    task.getDeleteReason(), task.getDescription(), task.getDueDate(), task.getEndTime(),
                    task.getFollowUpDate(), task.getName(), task.getOwner(), task.getParentTaskId(), task.getPriority(),
                    task.getStartTime(), task.getTaskDefinitionKey(), shipment.trackingId);
            completedHistoricTaskInstancesList.add(historicInstanceTask);
        }
        return completedHistoricTaskInstancesList;
    }
}
