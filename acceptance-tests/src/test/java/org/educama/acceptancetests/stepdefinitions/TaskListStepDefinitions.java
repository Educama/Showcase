package org.educama.acceptancetests.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.educama.acceptancetests.steps.TaskListSteps;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Step Definitions for the task list.
 */
public class TaskListStepDefinitions {
    @Steps
    TaskListSteps user;

    @BeforeScenario
    public void clearDatabaseBeforeScenario() {
        // TODO Clear the Database before Scenario starts
    }

    @Given(value = "there are $count tasks with name '$description' assigned to $assignee")
    public void givenThereAreNumberTasksWithNameCheckShipmentAssignedToName(int count, String description, String assignee) {
        // TODO Create some Tasks and delegate them to the assignee
    }

    @When("Tom requests the list of tasks")
    public void whenTomRequestsTheListOfTasks() {
        user.openTaskListPage();
    }

    @Then("Tom sees $count tasks with name 'Check shipment'")
    public void thenISeeTheTasksInTheTasklist(int count) {
        user.checksTheTaskList(count);
    }

    @Then("Tom sees an empty list")
    public void thenISeeAnEmptyList() {
        user.checksTheTaskList(0);
    }

}

