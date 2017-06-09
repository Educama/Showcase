import {Routes, RouterModule} from "@angular/router";
import {CustomerListPageComponent} from "../customer-list/container/customer-list-page.component";
import {CustomerCapturePageComponent} from "../customer-capture/container/customer-capture-page.component";

/*
 * Router configuration for the component task
 */
const CUSTOMER_ROUTES: Routes = [
    {
        path: "customers",
        component: CustomerListPageComponent
    },
    {
        path: "customers/edit/:id",
        component: CustomerCapturePageComponent
    },
    {
        path: "customers/capture",
        component: CustomerCapturePageComponent
    }
];

export const CUSTOMER_ROUTING = RouterModule.forChild(CUSTOMER_ROUTES);