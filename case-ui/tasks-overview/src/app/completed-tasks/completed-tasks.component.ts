import {Component} from "@angular/core";
import {
  Task,
  TaskService,
  TaskType
} from "../core/task/task.service";

@Component({
  selector: "app-completed-tasks",
  templateUrl: "./completed-tasks.component.html",
  styleUrls: ["./completed-tasks.component.scss"]
})
export class CompletedTasksComponent {
  constructor(private taskService: TaskService) {
  }

  retrieveCompletedTasks(): Task[] {
    return this.taskService.getTasks(TaskType.COMPLETED);
  }
}
