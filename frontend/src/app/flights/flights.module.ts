import {NgModule} from "@angular/core";
import {SharedModule} from "../common/shared.module";
import {FLIGHTS_ROUTING} from "./flights-common/routes/flights.routes";
import {AirportService} from "./flights-common/api/airports/airport.service";
import {AirportListModule} from "./airport-list/airport-list.module";
import {FlightsCommonModule} from "./flights-common/flights-common.module";

@NgModule({
    imports: [
        AirportListModule,
        FlightsCommonModule,
        FLIGHTS_ROUTING
    ],
    providers: [
      AirportService
    ]
})
export class FlightsModule {
}
