import {NgModule} from "@angular/core";
import {CaseUiCommonModule} from "./case-ui-common/case-ui-common.module";
import {CaseUiPageModule} from "./case-ui-page/case-ui-page.module";

@NgModule({
  imports: [
    CaseUiCommonModule,
    CaseUiPageModule
  ]
})
export class CaseUiModule {
}
