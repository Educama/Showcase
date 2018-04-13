import {NgModule} from "@angular/core";
import {ShipmentCaptureModule} from "./shipment-capture/shipment-capture.module";
import {ShipmentListModule} from "./shipment-list/shipment-list.module";
import {TaskListModule} from "./task-list/task-list.module";
import {ShipmentCommonModule} from "./shipment-common/shipment-common.module";
import {ShipmentRoutingModule} from "./shipment-routing/shipment-routing.module";
import {EnabledTaskListModule} from "./enabled-task-list/enabled-task-list.module";
import {CompletedTaskListModule} from "./completed-task-list/completed-task-list.module";
import {OrganizeFlightFormModule} from "../flights/organize-flight-form/organize-flight-form.module";
import {ShipmentInvoicePageComponent} from "./shipment-invoice/container/shipment-invoice-page.component";
import {ShipmentInvoiceModule} from "./shipment-invoice/shipment-invoice.module";

@NgModule({
    imports: [
      ShipmentCaptureModule,
      ShipmentCommonModule,
      ShipmentListModule,
      ShipmentRoutingModule,
      TaskListModule,
      EnabledTaskListModule,
      CompletedTaskListModule,
      OrganizeFlightFormModule,
      ShipmentInvoiceModule
    ]
})
export class ShipmentModule {
}
