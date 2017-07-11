import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {AirportService} from "../../api/airports/airport.service";
import * as actions from "../store/airport-list-page.actions";
import {
    RequestAirportsAction,
    RequestAirportsFailedAction,
    RequestAirportsSuccessfulAction
} from "../store/airport-list-page.actions";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {Observable} from "rxjs/Observable";
import {AirportListSlice} from "../store/airport-list-page.slice";
import "rxjs/Rx";

@Injectable()
export class AirportListEffect {

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
        .debounceTime(this.REQUEST_AIRPORTS_DEBOUNCE_TIME)
        .switchMap(() => Observable.of(new RequestAirportsAction()));

    @Effect() loadAirports = this._actions
        .ofType(actions.REQUEST_AIRPORTS)
        .withLatestFrom(this._store, (action, state) => state.airportListSlice)
        .switchMap((airportListSlice: AirportListSlice) => {
            return this._airportService.findAirports(
                airportListSlice.pageNumber, airportListSlice.pageSize,
                airportListSlice.sortBy, airportListSlice.sortOrder)
        })
        .map(taskListResource => new RequestAirportsSuccessfulAction(taskListResource))
        .catch(() => Observable.of(new RequestAirportsFailedAction()));

}