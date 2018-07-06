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
  selector: "app-active-tasks",
  templateUrl: "./active-tasks.component.html",
  styleUrls: [ "./active-tasks.component.scss" ]
})
export class ActiveTasksComponent {
  private readonly EVENT_DOMAIN: string = "http://localhost:8080";

  constructor(private taskService: TaskService,
              private postRobotService: PostRobotService) {
  }

  retrieveActiveTasks(): Task[] {
    return this.taskService.getTasks(TaskType.ACTIVE);
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
