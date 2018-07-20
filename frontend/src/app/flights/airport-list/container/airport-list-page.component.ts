import { distinctUntilChanged } from "rxjs/operators";
import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import {
  ChangeAirportsPageAction,
  ChangeAirportsSortingAction,
  InitializeAirportSliceAction,
  RequestAirportsAction
} from "../../flights-common/store/airports/airports.actions";
import { AirportSlice } from "../../flights-common/store/airports/airports.slice";
import { SortOrder } from "../../../shared/enums/sort-order.enum";
import { LazyLoadEvent } from "primeng/primeng";
import { TranslateService } from "@ngx-translate/core";
import { AirportResource } from "../../flights-common/api/airports/airport.resource";
import { Subscription } from "rxjs";
import * as _ from "lodash";

@Component({
  selector: "educama-airports-list-page",
  templateUrl: "./airport-list-page.component.html"
})
export class AirportsListPageComponent implements OnInit, OnDestroy {

  public airportList: AirportResource[];
  public pageNumber: number;
  public pageSize: number;
  public totalPages: number;
  public airportsLoading: boolean;

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
                                            .select(state => state.airportSlice)
                                            .pipe(distinctUntilChanged())
                                            .subscribe(airportListSlice => this._updateModel(airportListSlice));
  }

  public ngOnDestroy() {
    this.airportListSliceSubscription.unsubscribe();
    this._store.dispatch(new InitializeAirportSliceAction());
  }

  public onButtonRefresh(): void {
    this._store.dispatch(new RequestAirportsAction());
  }

  public loadAirportsLazy(event: LazyLoadEvent) {
    this._store.dispatch(new ChangeAirportsPageAction({
      pageNumber: event.first / event.rows,
      pageSize: event.rows
    }));
    if (!_.isUndefined(event.sortField)) {
      const sortOrder: SortOrder = event.sortOrder === 1 ? SortOrder.ascending : SortOrder.desending;
      this._store.dispatch(new ChangeAirportsSortingAction({ sortBy: event.sortField, sortOrder: sortOrder }));
    }
  }

  private _updateModel(airportListSlice: AirportSlice) {
    this.airportList = airportListSlice.airportList;
    this.pageNumber = airportListSlice.pageNumber;
    this.pageSize = airportListSlice.pageSize;
    this.totalPages = airportListSlice.totalPages;
    this.airportsLoading = airportListSlice.loading;
  }

}
