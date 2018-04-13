import {SharedModule} from "../../shared/shared.module";
import {NgModule} from "@angular/core";
import {ShipmentInvoiceComponent} from "./presentationals/shipment-invoice.component";
import {ShipmentInvoicePageComponent} from "./container/shipment-invoice-page.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    ShipmentInvoiceComponent,
    ShipmentInvoicePageComponent
  ],
  exports: [
    ShipmentInvoiceComponent
  ]
})
export class ShipmentInvoiceModule {
}
