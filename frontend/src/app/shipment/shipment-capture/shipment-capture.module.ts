import {NgModule} from "@angular/core";
import {ShipmentCapturePageComponent} from "./container/shipment-capture-page.component";
import {ShipmentCaptureComponent} from "./presentationals/shipment-capture.component";
import {SharedModule} from "../../common/shared.module";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    ShipmentCapturePageComponent,
    ShipmentCaptureComponent,
  ],
  exports: [
    ShipmentCapturePageComponent
  ]
})
export class ShipmentCaptureModule {
}
