import { Component } from "@angular/core";
import {
  Task,
  TaskService,
  TaskType
} from "../core/task/task.service";
import {
  CONSTANTS_SHARED,
  PostRobotService
} from "frontend-shared";

@Component({
  selector: "app-available-tasks",
  templateUrl: "./available-tasks.component.html",
  styleUrls: [ "./available-tasks.component.scss" ]
})
export class AvailableTasksComponent {
  private readonly EVENT_DOMAIN: string = "http://localhost:8080";

  constructor(private taskService: TaskService,
              private postRobotService: PostRobotService) {
  }

  retrieveAvailableTasks(): Task[] {
    return this.taskService.getTasks(TaskType.AVAILABLE);
  }

  loadDynamicFrame(src: string): void {
    this.postRobotService.getInstance()
        .send(
          window.top,
          CONSTANTS_SHARED.POST_ROBOT.EVENT_NAME,
          { url: src },
          { domain: this.EVENT_DOMAIN }
        ).then(() => {
    });
  }
}
