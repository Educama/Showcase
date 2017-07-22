import {NgModule} from "@angular/core";
import {SharedModule} from "../common/shared.module";
import {CUSTOMER_ROUTING} from "./routes/customer.routes";
import {CustomerService} from "./api/customer.service";
import {CustomerListPageComponent} from "./customer-list/container/customer-list-page.component";
import {CustomerListComponent} from "./customer-list/components/customer-list.component";
import {CustomerCapturePageComponent} from "./customer-capture/container/customer-capture-page.component";
import {CustomerCaptureComponent} from "./customer-capture/components/customer-capture.component";
import {EffectsModule} from "@ngrx/effects";
import {CustomerListEffect} from "./customer-list/effects/customer-list.effect";
import {CustomerCaptureEffect} from "./customer-capture/effects/customer-capture.effects";

@NgModule({
  imports: [
    SharedModule,
    CUSTOMER_ROUTING,
    EffectsModule.forFeature([
      CustomerListEffect,
      CustomerCaptureEffect
    ])
  ],
  declarations: [CustomerCapturePageComponent, CustomerCaptureComponent,
    CustomerListPageComponent, CustomerListComponent],
  exports: [CustomerListPageComponent, CustomerCapturePageComponent],
  providers: [CustomerService]
})
export class CustomerModule {
}
