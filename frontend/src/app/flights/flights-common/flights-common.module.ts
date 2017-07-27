import {NgModule} from "@angular/core";
import {SharedModule} from "../../common/shared.module";
import {EffectsModule} from "@ngrx/effects";
import {AirportService} from "./api/airports/airport.service";
import {AirportListEffect} from "./efffects/airport-list.effect";
import {FLIGHTS_ROUTING} from "./routes/flights.routes";

@NgModule({
  imports: [
    EffectsModule.forFeature([
      AirportListEffect
    ]),
    FLIGHTS_ROUTING,
    SharedModule
  ],
  providers: [
    AirportService
  ]
})
export class FlightsCommonModule {
}
