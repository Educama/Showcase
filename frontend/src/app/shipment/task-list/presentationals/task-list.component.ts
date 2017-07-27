import {Component, Input, Output, EventEmitter} from "@angular/core";
import {TaskResource} from "../../shipment-common/api/resources/task.resource";
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

    @Output()
    public taskSelectedEvent: EventEmitter<string> = new EventEmitter();

    public onRowSelect(event: Event) {
        this.taskSelectedEvent.emit(this.selectedTask.trackingId);
    }
}
