import { of as observableOf } from "rxjs";
import {
  catchError,
  debounceTime,
  map,
  switchMap,
  withLatestFrom
} from "rxjs/operators";
import { Injectable } from "@angular/core";
import {
  Actions,
  Effect
} from "@ngrx/effects";
import { AirportService } from "../../api/airports/airport.service";
import * as actions from "./airports.actions";
import {
  RequestAirportsAction,
  RequestAirportsFailedAction,
  RequestAirportsSuccessfulAction
} from "./airports.actions";
import { Store } from "@ngrx/store";
import { State } from "../../../../app.reducers";
import { AirportSlice } from "./airports.slice";

@Injectable()
export class AirportEffects {

  private readonly TRIGGER_REQUEST_AIRPORTS_ACTIONS: string[] = [
    actions.CHANGE_AIRPORTS_PAGE,
    actions.CHANGE_AIRPORTS_SORTING
  ];

  private readonly REQUEST_AIRPORTS_DEBOUNCE_TIME: number = 300;

  constructor(private _actions: Actions,
              private _airportService: AirportService,
              private _store: Store<State>) {
  }

  @Effect() triggerLoadAirports = this._actions
                                      .ofType(...this.TRIGGER_REQUEST_AIRPORTS_ACTIONS)
                                      .pipe(
                                        debounceTime(this.REQUEST_AIRPORTS_DEBOUNCE_TIME),
                                        switchMap(() => observableOf(new RequestAirportsAction()))
                                      );

  @Effect() loadAirports = this._actions
                               .ofType(actions.REQUEST_AIRPORTS)
                               .pipe(
                                 withLatestFrom(this._store, (action, state) => state.airportSlice),
                                 switchMap((airportListSlice: AirportSlice) => {
                                   return this._airportService.findAirports(
                                     airportListSlice.pageNumber, airportListSlice.pageSize,
                                     airportListSlice.sortBy, airportListSlice.sortOrder);
                                 }),
                                 map(airportListResource => new RequestAirportsSuccessfulAction(airportListResource)),
                                 catchError(() => observableOf(new RequestAirportsFailedAction()))
                               );

}
