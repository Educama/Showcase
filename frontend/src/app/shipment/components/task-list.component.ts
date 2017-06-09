import {Component, Input, Output} from "@angular/core";
import {TaskResource} from "../api/resources/task.resource";
import {ShipmentListRowModel} from "../container/shipment-list-page.model";
import {TaskListRowModel} from "../container/task-list-page.model";

@Component({
    selector: "educama-task-list",
    templateUrl: "./task-list.component.html"
})
export class TaskListComponent {

    @Input()
    public taskList: TaskListRowModel[];

    @Output()
    public selectedTask: TaskResource = new TaskResource();

}
