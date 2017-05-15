package org.educama.acceptancetests.steps;

import net.thucydides.core.annotations.Step;
import org.educama.acceptancetests.pages.TaskListPage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Steps for Task list.
 */
public class TaskListSteps {

    TaskListPage taskListPage;

    @Step
    public void openTaskListPage() {
        taskListPage.open();
    }

    @Step
    public void checksTheTaskList(int count) {
        assertThat("There are " + count + " Tasks in the Task list.", taskListPage.getTaskList().size() == count, is(true));
    }

}
