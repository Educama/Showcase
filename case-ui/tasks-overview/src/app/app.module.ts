import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {ActiveTasksComponent} from "./active-tasks/active-tasks.component";
import {AvailableTasksComponent} from "./available-tasks/available-tasks.component";
import {CompletedTasksComponent} from "./completed-tasks/completed-tasks.component";
import {CoreModule} from "./core/core.module";

@NgModule({
  declarations: [
    AppComponent,
    ActiveTasksComponent,
    AvailableTasksComponent,
    CompletedTasksComponent
  ],
  imports: [
    BrowserModule,
    CoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
