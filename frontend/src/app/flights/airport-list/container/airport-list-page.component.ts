import {Component, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {InitializeAirportListAction, RequestAirportsAction} from "../../flights-common/store/airport-list-page.actions";

@Component({
    selector: "educama-airports-list-page",
    templateUrl: "./airport-list-page.component.html"
})
export class AirportsListPageComponent implements OnDestroy {

    constructor(private _store: Store<State>,
                private _router: Router) {
    }

    public ngOnDestroy() {
        this._store.dispatch(new InitializeAirportListAction());
    }

    /*
     * Refresh the airports list by re-loading the tasks from the server
     */
    public onButtonRefresh(): void {
        this._store.dispatch(new RequestAirportsAction());
    }

}
