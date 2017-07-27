import {NgModule} from "@angular/core";
import {SharedModule} from "../../common/shared.module";
import {TaskListPageComponent} from "./container/task-list-page.component";
import {TaskListComponent} from "./presentationals/task-list.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    TaskListPageComponent,
    TaskListComponent
  ],
  exports: [
    TaskListPageComponent
  ]
})
export class TaskListModule {
}
