import {NgModule} from "@angular/core";
import {SharedModule} from "../common/shared.module";
import {FLIGHTS_ROUTING} from "./routes/flights.routes";
import {AirportsListPageComponent} from "./airport-list/container/airports-list-page.component";
import {AirportService} from "./api/airports/airport.service";
import {AirportListComponent} from "./airport-list/presentationals/airport-list.component";
import {EffectsModule} from "@ngrx/effects";
import {AirportListEffect} from "./airport-list/efffects/airport-list.effect";

@NgModule({
    imports: [
        SharedModule,
        FLIGHTS_ROUTING,
        EffectsModule.run(AirportListEffect)
    ],
    declarations: [AirportsListPageComponent, AirportListComponent],
    exports: [AirportsListPageComponent],
    providers: [AirportService]
})
export class FlightsModule {
}
