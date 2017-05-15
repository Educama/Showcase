package org.educama.acceptancetests.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Page (see "Page Object Pattern") for the list of tasks.
 */

@DefaultUrl("http://localhost:8090/tasks")
public class TaskListPage extends PageObject {

    @FindBy(id = "task-list-component_task-table")
    WebElement listOfTasks;

    public List<String> getTaskList() {
        // Get all the TR elements from the table
        List<WebElement> allRows = listOfTasks.findElements(By.tagName("tr"));
        // And iterate over them, getting the cells
        return allRows.stream()
                .map(row -> {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    // row must have more than one column. Otherwise it is the "No records found." message.
                    return cells.size() > 1 ? cells.get(0).getText() : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
