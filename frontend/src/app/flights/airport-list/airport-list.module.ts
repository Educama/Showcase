import {NgModule} from "@angular/core";
import {SharedModule} from "../../common/shared.module";
import {AirportsListPageComponent} from "./container/airport-list-page.component";
import {AirportListComponent} from "./presentationals/airport-list.component";
import {EffectsModule} from "@ngrx/effects";
import {AirportListEffect} from "../flights-common/efffects/airport-list.effect";

@NgModule({
  imports: [
    SharedModule,
    EffectsModule.forFeature([
      AirportListEffect
    ])
  ],
  declarations: [
    AirportsListPageComponent,
    AirportListComponent
  ],
  exports: [
    AirportsListPageComponent
  ]
})
export class AirportListModule {
}
