import {RouterModule, Routes} from "@angular/router";
import {AirportsListPageComponent} from "../airport-list/container/airports-list-page.component";

/*
 * Router configuration for the component fligt
 */
const FLIGHTS_ROUTES: Routes = [
    {
        path: "airports",
        component: AirportsListPageComponent
    }
];

export const FLIGHTS_ROUTING = RouterModule.forChild(FLIGHTS_ROUTES);