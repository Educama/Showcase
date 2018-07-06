import { NgModule } from "@angular/core";
import { TaskService } from "./task/task.service";
import {
  PostRobotService,
  WindowRefService
} from "frontend-shared";

@NgModule({
  providers: [
    TaskService,
    WindowRefService,
    PostRobotService
  ]
})
export class CoreModule {
}
