import {Routes, RouterModule} from "@angular/router";
import {ShipmentListPageComponent} from "../container/shipment-list-page.component";
import {ShipmentCaptureComponent} from "../components/shipment-capture.component";
import {ShipmentCapturePageComponent} from "../container/shipment-capture-page.component";
import {TabViewComponent} from "../container/tabview-page.component";

/*
 * Router configuration for the component task
 */
const SHIPMENT_ROUTES: Routes = [
    {
        path: 'shipments',
        component: ShipmentListPageComponent
    },
    {
        path: 'shipments/capture',
        component: ShipmentCapturePageComponent
    },
    {
        path: 'home',
        component: TabViewComponent
    },
];

export const SHIPMENT_ROUTING = RouterModule.forChild(SHIPMENT_ROUTES);