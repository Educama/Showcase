import {Component, Input, Output} from "@angular/core";
import {TaskResource} from "../api/resources/task.resource";

@Component({
    selector: "educama-task-list",
    templateUrl: "./task-list.component.html"
})
export class TaskListComponent {

    @Input()
    public taskList: TaskResource[];

    @Output()
    public selectedTask: TaskResource = new TaskResource();

}
