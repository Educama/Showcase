import {Component, OnDestroy, OnInit} from "@angular/core";
import {AirportResource} from "../../api/airports/airport.resource";
import {TranslateService} from "ng2-translate";
import {LazyLoadEvent} from "primeng/components/common/api";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {ChangeAirportsPageAction, ChangeAirportsSortingAction} from "../store/airport-list-page.actions";
import {Subscription} from "rxjs/Subscription";
import {AirportListSlice} from "../store/airport-list-page.slice";
import * as _ from "lodash";
import {SortOrder} from "../../../common/enums/sort-order.enum";

@Component({
    selector: "educama-airport-list",
    templateUrl: "./airport-list.component.html"
})
export class AirportListComponent implements OnInit, OnDestroy {

    public airportList: AirportResource[];
    public pageNumber: number;
    public pageSize: number;
    public totalPages: number;
    public airportsLoading:boolean;

    // relevant slice of store and subscription for this slice
    public airportListSliceSubscription: Subscription;

    public emptyListMessage: string;

    constructor(private _store: Store<State>,
                private _translateService: TranslateService) {

        this._translateService
            .get("GENERIC_NO-RECORDS-FOUND")
            .subscribe(value => this.emptyListMessage = value);
    }

    public ngOnInit() {
        this.airportListSliceSubscription = this._store
            .select(state => state.airportListSlice)
            .distinctUntilChanged()
            .subscribe(airportListSlice => this._updateModel(airportListSlice));
    }

    public ngOnDestroy() {
        this.airportListSliceSubscription.unsubscribe();
    }

    public loadAirportsLazy(event: LazyLoadEvent) {
        this._store.dispatch(new ChangeAirportsPageAction({
            pageNumber: event.first / event.rows,
            pageSize: event.rows
        }));
        if (!_.isUndefined(event.sortField)) {
            let sortOrder: SortOrder = event.sortOrder === 1 ? SortOrder.ascending : SortOrder.desending;
            this._store.dispatch(new ChangeAirportsSortingAction({sortBy: event.sortField, sortOrder: sortOrder}));
        }
    }

    private _updateModel(airportListSlice: AirportListSlice) {
        this.airportList = airportListSlice.airportList;
        this.pageNumber = airportListSlice.pageNumber;
        this.pageSize = airportListSlice.pageSize;
        this.totalPages = airportListSlice.totalPages;
        this.airportsLoading = airportListSlice.loading;
    }
}
