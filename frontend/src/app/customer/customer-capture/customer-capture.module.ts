import {NgModule} from "@angular/core";
import {SharedModule} from "../../common/shared.module";
import {CustomerCapturePageComponent} from "./container/customer-capture-page.component";
import {CustomerCaptureComponent} from "./presentationals/customer-capture.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    CustomerCapturePageComponent,
    CustomerCaptureComponent
  ],
  exports: [
    CustomerCapturePageComponent
  ]
})
export class CustomerCaptureModule {
}
