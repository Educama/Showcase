import { of as observableOf } from "rxjs";
import {
  catchError,
  debounceTime,
  map,
  switchMap,
  withLatestFrom
} from "rxjs/operators";
import { Injectable } from "@angular/core";
import * as actions from "./airlines.actions";
import {
  Actions,
  Effect
} from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { AirlineService } from "../../api/airlines/airline.service";
import { State } from "../../../../app.reducers";
import { AirlineSlice } from "./airlines.slice";

@Injectable()
export class AirlineEffects {

  private readonly TRIGGER_REQUEST_AIRLINES_ACTIONS: string[] = [
    actions.CHANGE_AIRLINES_PAGE,
    actions.CHANGE_AIRLINES_SORTING
  ];

  private readonly REQUEST_AIRLINES_DEBOUNCE_TIME: number = 300;

  constructor(private _actions: Actions,
              private _airlineService: AirlineService,
              private _store: Store<State>) {
  }

  @Effect() triggerLoadAirlines = this._actions
                                      .ofType(...this.TRIGGER_REQUEST_AIRLINES_ACTIONS)
                                      .pipe(
                                        debounceTime(this.REQUEST_AIRLINES_DEBOUNCE_TIME),
                                        switchMap(() => observableOf(new actions.RequestAirlinesAction()))
                                      );

  @Effect() loadAirlines = this._actions
                               .ofType(actions.REQUEST_AIRLINES)
                               .pipe(
                                 withLatestFrom(this._store, (action, state) => state.airlineSlice),
                                 switchMap((airlineListSlice: AirlineSlice) => {
                                   return this._airlineService.findAirlines(
                                     airlineListSlice.pageNumber, airlineListSlice.pageSize,
                                     airlineListSlice.sortBy, airlineListSlice.sortOrder);
                                 }),
                                 map(airlineListResource => new actions.RequestAirlinesSuccessfulAction(airlineListResource)),
                                 catchError(() => observableOf(new actions.RequestAirlinesFailedAction()))
                               );

}
