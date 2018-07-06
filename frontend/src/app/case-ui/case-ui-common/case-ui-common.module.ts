import { NgModule } from "@angular/core";
import { CASE_UI_ROUTING } from "./routes/case-ui.routes";
import { PostRobotService } from "frontend-shared";

@NgModule({
  imports: [
    CASE_UI_ROUTING
  ],
  providers: [
    PostRobotService
  ]
})
export class CaseUiCommonModule {
}
