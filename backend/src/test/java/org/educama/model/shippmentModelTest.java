package org.educama.model;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.init;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.*;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class shippmentModelTest {

	ProcessEngine processEngine;
	CaseService caseService;
	HistoryService historyService;
	RuntimeService runtimeService;
	TaskService taskService;

	CaseInstance caseInstance;
	CaseExecution caseExecution;


	final String CASE_KEY = "CaseId_shipment";
	
	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();
	
	@Before
	public void setup() {
		init(rule.getProcessEngine());
		processEngine = rule.getProcessEngine();
		caseService = processEngine.getCaseService();
		historyService = processEngine.getHistoryService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();

		// create the case instance
		caseInstance = processEngine.getCaseService().createCaseInstanceByKey(CASE_KEY);
		caseExecution = caseService.createCaseExecutionQuery().caseExecutionId(caseInstance.getId()).singleResult();

	}


	@Test
	@Deployment(resources = "cmmn/shipment.cmmn")
	public void testShipmentCmmnModel() {
		
		//first task is active
		Task createShipmentTask = taskService.createTaskQuery().taskDefinitionKey("HumanTask_createShipment")
				.singleResult();
		assertTrue(caseService.createCaseExecutionQuery().caseExecutionId(createShipmentTask.getCaseExecutionId()).singleResult().isActive());
		showCaseOverview();
		//complete first task to activate the second
		complete(createShipmentTask);
		String processShipmentId = caseService.createCaseExecutionQuery().enabled().activityId("HumanTask_processShipment").singleResult().getId();
		caseService.manuallyStartCaseExecution(processShipmentId);
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().list();
	    assertEquals(1, tasks.size());
	    assertThat(tasks.get(0)).hasDefinitionKey("HumanTask_processShipment");
		showCaseOverview();
		Task prcoessShipmentTask = taskService.createTaskQuery().taskDefinitionKey("HumanTask_processShipment").singleResult();
		//complete second task
		complete(prcoessShipmentTask);
		tasks = processEngine.getTaskService().createTaskQuery().list();
		assertEquals(0, tasks.size());
		showCaseOverview();
		//case instance is completed
		assertThat(caseInstance.isCompleted());
		
	}
	
	//method to display an overview of executions
	private void showCaseOverview() {
		List<CaseExecution> caseExecutionList = caseService.createCaseExecutionQuery()
				.caseInstanceId(caseInstance.getId()).list();
		System.out.println("------ Current List of Case Executions ------");
		
		caseExecutionList.stream().filter(caseExecution -> caseExecution.getId() == caseInstance.getId())
				.forEach(caseExecution -> System.out.println("Case Instance : "
						+ caseExecution.getActivityName() + " [" + caseExecution.getActivityType() + "]"
						+ " - CaseExecutionId: " + caseExecution.getId()));

		caseExecutionList.stream().filter(caseExecution -> caseExecution.isActive())
				.forEach(caseExecution -> System.out.println("Running ('active'): " 
						+ caseExecution.getActivityName() + " [" + caseExecution.getActivityType() + "]" 
						+ " - CaseExecutionId: " + caseExecution.getId()));

		caseExecutionList.stream().filter(caseExecution -> caseExecution.isEnabled())
				.forEach(caseExecution -> System.out.println("Possible to start ('enabled'): "
						+ caseExecution.getActivityName() + " [" + caseExecution.getActivityType() + "]"
						+ " - CaseExecutionId: " + caseExecution.getId()));

		caseExecutionList.stream().filter(c -> c.isAvailable()).filter(c -> c.getActivityType().compareTo("milestone")!=0)
				.forEach(caseExecution -> System.out.println("Impossible to start ('available'): "
						+ caseExecution.getActivityName() + " [" + caseExecution.getActivityType() + "]"
						+ " - CaseExecutionId: " + caseExecution.getId()));
		
		caseExecutionList.stream().filter(c -> c.isAvailable()).filter(c -> c.getActivityType().compareTo("milestone")==0)
				.forEach(caseExecution -> System.out.println("Milestone not reached yet: "
						+ caseExecution.getActivityName()
						+ " - CaseExecutionId: " + caseExecution.getId()));
		
		System.out.println("---------------------------------------------");
		System.out.println();
	}
	
}
