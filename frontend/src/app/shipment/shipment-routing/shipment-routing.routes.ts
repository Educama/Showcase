import {Routes} from "@angular/router";
import {ShipmentListPageComponent} from "../shipment-list/container/shipment-list-page.component";
import {TaskListPageComponent} from "../task-list/container/task-list-page.component";
import {ShipmentCapturePageComponent} from "../shipment-capture/container/shipment-capture-page.component";
import {CaseUiComponent} from "../caseUI/caseUi.component";
import {EnabledTaskListPageComponent} from "../enabled-task-list/container/enabled-task-list-page.component";
import {OrganizeFlightFormPageComponent} from "../../flights/organize-flight-form/container/organize-flight-form-page.component";
import {ShipmentInvoicePageComponent} from "../shipment-invoice/container/shipment-invoice-page.component";

/*
 * Router configuration for the component task
 */
export const SHIPMENT_ROUTES: Routes = [
    {
        path: "shipments",
        component: ShipmentListPageComponent
    },
    {
        path: "shipments/capture",
        component: ShipmentCapturePageComponent
    },
  {
    path: "shipments/edit/:id",
    component: ShipmentCapturePageComponent
  },
    {
        path: "tasks/active",
        component: TaskListPageComponent
    },
    {
        path: "caseui/:id",
        component: CaseUiComponent,
            children: [
              { path: "changeShipment", component: ShipmentCapturePageComponent },
              { path: "completeShipment", component: ShipmentCapturePageComponent},
              { path: "organizeFlight", component: OrganizeFlightFormPageComponent},
              { path: "createInvoice",  component: ShipmentInvoicePageComponent}
            ]
    },
    {
        path: "tasks/enabled",
        component: EnabledTaskListPageComponent
    },
];
