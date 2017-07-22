import {Routes, RouterModule} from "@angular/router";
import {ShipmentListPageComponent} from "../container/shipment-list-page.component";
import {ShipmentCaptureComponent} from "../components/shipment-capture.component";
import {TaskListPageComponent} from "../container/task-list-page.component";
import {ShipmentCapturePageComponent} from "../container/shipment-capture-page.component";

/*
 * Router configuration for the component task
 */
const SHIPMENT_ROUTES: Routes = [
    {
        path: "shipments",
        component: ShipmentListPageComponent
    },
    {
        path: "shipments/capture",
        component: ShipmentCapturePageComponent
    },
    {
        path: "tasks",
        component: TaskListPageComponent
    },
    {
        path: "shipments/edit/:id",
        component: ShipmentCapturePageComponent
    },
];

export const SHIPMENT_ROUTING = RouterModule.forChild(SHIPMENT_ROUTES);
