import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {ShipmentListPageComponent} from "./container/shipment-list-page.component";
import {ShipmentListComponent} from "./presentationals/shipment-list.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    ShipmentListPageComponent,
    ShipmentListComponent,
  ],
  exports: [
    ShipmentListPageComponent
  ]
})
export class ShipmentListModule {
}
