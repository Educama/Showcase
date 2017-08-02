import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {CUSTOMER_ROUTING} from "./routes/customer.routes";
import {EffectsModule} from "@ngrx/effects";
import {CustomerListEffect} from "./effects/customer-list.effect";
import {CustomerCaptureEffect} from "./effects/customer-capture.effects";
import {CustomerService} from "./api/customer.service";

@NgModule({
  imports: [
    CUSTOMER_ROUTING,
    SharedModule,
    EffectsModule.forFeature([
      CustomerListEffect,
      CustomerCaptureEffect
    ])
  ],
  providers: [
    CustomerService
  ]
})
export class CustomerCommonModule {
}
