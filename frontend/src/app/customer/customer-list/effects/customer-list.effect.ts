import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {CustomerService} from "../../api/customer.service";
import * as actions from "../store/customer-list-page.actions";
import {
    RequestCustomersAction,
    RequestCustomersFailedAction,
    RequestCustomersSuccessfulAction
} from "../store/customer-list-page.actions";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {Observable} from "rxjs/Observable";
import {CustomerListSlice} from "../store/customer-list-page.slice";
import "rxjs/Rx";

@Injectable()
export class CustomerListEffect {

    private readonly TRIGGER_REQUEST_CUSTOMERS_ACTIONS: string[] = [
        actions.CHANGE_CUSTOMERS_PAGE
    ];

    private readonly REQUEST_CUSTOMERS_DEBOUNCE_TIME: number = 300;

    constructor(private _actions: Actions,
                private _customerService: CustomerService,
                private _store: Store<State>) {
    }

    @Effect() triggerLoadCustomers = this._actions
        .ofType(...this.TRIGGER_REQUEST_CUSTOMERS_ACTIONS)
        .debounceTime(this.REQUEST_CUSTOMERS_DEBOUNCE_TIME)
        .switchMap(() => Observable.of(new RequestCustomersAction()));

    @Effect() loadCustomers = this._actions
        .ofType(actions.REQUEST_CUSTOMERS)
        .withLatestFrom(this._store, (action, state) => state.customerListSlice)
        .switchMap((customerListSlice: CustomerListSlice) => {
            return this._customerService.findCustomers(customerListSlice.pageNumber, customerListSlice.pageSize)
        })
        .map(taskListResource => new RequestCustomersSuccessfulAction(taskListResource))
        .catch(() => Observable.of(new RequestCustomersFailedAction()));

}