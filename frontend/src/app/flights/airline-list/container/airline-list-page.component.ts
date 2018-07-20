import { distinctUntilChanged } from "rxjs/operators";
import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import { Subscription } from "rxjs";
import { AirlineResource } from "../../flights-common/api/airlines/airline.resource";
import { TranslateService } from "@ngx-translate/core";
import { LazyLoadEvent } from "primeng/primeng";
import {
  ChangeAirlinesPageAction,
  ChangeAirlinesSortingAction,
  InitializeAirlineSliceAction,
  RequestAirlinesAction
} from "../../flights-common/store/airlines/airlines.actions";
import { SortOrder } from "../../../shared/enums/sort-order.enum";
import * as _ from "lodash";
import { AirlineSlice } from "../../flights-common/store/airlines/airlines.slice";

@Component({
  selector: "educama-airline-list-page",
  templateUrl: "./airline-list-page.component.html"
})
export class AirlineListPageComponent implements OnInit, OnDestroy {

  public airlineList: AirlineResource[];
  public pageNumber: number;
  public pageSize: number;
  public totalPages: number;
  public airlineLoading: boolean;

  public emptyListMessage: string;

  private _airlineSliceSubscription: Subscription;

  constructor(private _store: Store<State>, private _translateService: TranslateService) {
    this._translateService
        .get("GENERIC_NO-RECORDS-FOUND")
        .subscribe(value => this.emptyListMessage = value);
  }

  public ngOnInit() {
    this._airlineSliceSubscription = this._store
                                         .select(state => state.airlineSlice)
                                         .pipe(distinctUntilChanged())
                                         .subscribe(airlineListSlice => this._updateModel(airlineListSlice));
  }

  public ngOnDestroy() {
    this._airlineSliceSubscription.unsubscribe();
    this._store.dispatch(new InitializeAirlineSliceAction());
  }

  public onButtonRefresh(): void {
    this._store.dispatch(new RequestAirlinesAction());
  }

  public loadAirlinesLazy(event: LazyLoadEvent) {
    this._store.dispatch(new ChangeAirlinesPageAction({
      pageNumber: event.first / event.rows,
      pageSize: event.rows
    }));
    if (!_.isUndefined(event.sortField)) {
      const sortOrder: SortOrder = event.sortOrder === 1 ? SortOrder.ascending : SortOrder.desending;
      this._store.dispatch(new ChangeAirlinesSortingAction({ sortBy: event.sortField, sortOrder: sortOrder }));
    }
  }

  private _updateModel(airlineListSlice: AirlineSlice) {
    this.airlineList = airlineListSlice.airlineList;
    this.pageNumber = airlineListSlice.pageNumber;
    this.pageSize = airlineListSlice.pageSize;
    this.totalPages = airlineListSlice.totalPages;
    this.airlineLoading = airlineListSlice.loading;
  }

}
