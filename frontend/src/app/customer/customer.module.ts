import {NgModule} from "@angular/core";
import {CustomerListModule} from "./customer-list/customer-list.module";
import {CustomerCaptureModule} from "./customer-capture/customer-capture.module";
import {CustomerCommonModule} from "./customer-common/customer-common.module";

@NgModule({
  imports: [
    CustomerCaptureModule,
    CustomerCommonModule,
    CustomerListModule
  ]
})
export class CustomerModule {
}
