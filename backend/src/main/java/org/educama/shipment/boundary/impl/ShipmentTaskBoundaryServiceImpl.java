package org.educama.shipment.boundary.impl;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.task.Task;
import org.educama.shipment.api.datastructure.EnabledTaskDS;
import org.educama.shipment.api.datastructure.ShipmentTaskDS;
import org.educama.shipment.api.datastructure.CompletedTaskDS;
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
@Transactional(readOnly = false)
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
        return  shipmentTaskFromTask(tasks);
    }

    @Override
    public List<ShipmentTaskDS> findAllActiveForShipment(String trackingId) {
        Collection<Task> active = taskService.createTaskQuery().caseInstanceBusinessKey(trackingId).active().list();
        return  shipmentTaskFromTask(active);
    }

    private  List<ShipmentTaskDS> shipmentTaskFromTask(Collection<Task> tasks) {
        List<ShipmentTaskDS> shipmentTasks = new ArrayList<ShipmentTaskDS>();
        for (Task task : tasks) {
            CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceId(task.getCaseInstanceId()).active().singleResult();
            Shipment shipment = shipmentRepository.findOneBytrackingId(caseInstance.getBusinessKey());
            ShipmentTaskDS shipmentTaskDS = new ShipmentTaskDS(task.getCreateTime(), shipment.trackingId, task.getId(),
                    task.getName(), task.getDescription(), task.getAssignee(), shipment.sender, shipment.receiver,
                    task.getDueDate());
            shipmentTasks.add(shipmentTaskDS);
        }
        return shipmentTasks;
    }

    @Override
    public List<CompletedTaskDS> findAllCompletedTasksForShipment(String trackingId) {
        List<CompletedTaskDS> completedShipmentTasks = new ArrayList<CompletedTaskDS>();
        CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceBusinessKey(trackingId).singleResult();
        Shipment shipment = shipmentRepository.findOneBytrackingId(caseInstance.getBusinessKey());

        List<HistoricTaskInstance> historicTaskInstancesList = historyService.createHistoricTaskInstanceQuery()
                .caseInstanceId(caseInstance.getId()).finished().list();

        for (HistoricTaskInstance task : historicTaskInstancesList) {

            CompletedTaskDS completedTask = new CompletedTaskDS(shipment.trackingId, task.getId(), task.getName(),
                    task.getDescription(), task.getAssignee(), task.getEndTime());
            completedShipmentTasks.add(completedTask);
        }
        return completedShipmentTasks;
    }

    @Override
    public List<EnabledTaskDS> findAllEnabledTasksForShipment(String trackingId) {
        Collection<CaseExecution> caseExecutions = caseService.createCaseExecutionQuery().enabled().list();
        List<EnabledTaskDS> enabledShipmentTasks = new ArrayList<>();

        for (CaseExecution caseExecution : caseExecutions) {

            CaseInstance caseInstance = caseService.createCaseInstanceQuery().caseInstanceId(caseExecution.getCaseInstanceId()).active().singleResult();

            if (caseInstance.getBusinessKey().equals(trackingId)) {
                EnabledTaskDS enabledTaskDS = new EnabledTaskDS(caseExecution.getActivityDescription(), caseExecution.getActivityId(),
                        caseExecution.getActivityName(), caseExecution.getActivityType(), trackingId);
                enabledShipmentTasks.add(enabledTaskDS);
            }
        }
        return enabledShipmentTasks;
    }

    @Override
    public void manuallyStartEnabledTask(String trackingID, String name) {
        Collection<CaseExecution> caseExecutions =
                caseService.createCaseExecutionQuery().caseInstanceBusinessKey(trackingID).list();

        caseExecutions.stream().filter(caseExecution -> caseExecution.getActivityName().equals(name == null ? " " : name)).
                findFirst().ifPresent(caseExecution -> caseService.manuallyStartCaseExecution(caseExecution.getId()));
    }

    @Override
    public void completeTask(String trackingID, String name) {
        Collection<CaseExecution> caseExecutions =
                caseService.createCaseExecutionQuery().caseInstanceBusinessKey(trackingID).list();

        caseExecutions.stream().filter(caseExecution -> caseExecution.getActivityName().equals(name == null ? " " : name)).
                findFirst().ifPresent(caseExecution -> caseService.completeCaseExecution(caseExecution.getId()));
    }
}
