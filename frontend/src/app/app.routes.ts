import {Routes, RouterModule} from "@angular/router";

const routes: Routes = [
    {
        path: "",
        redirectTo: "shipments",
        pathMatch: "full"
    }
];

export const APP_ROUTING_PROVIDERS: any[] = [];

export const ROUTING = RouterModule.forRoot(routes);