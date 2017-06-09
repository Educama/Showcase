import {Component, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import "rxjs/add/operator/distinctUntilChanged";
import {InitializeCustomerListAction, RequestCustomersAction} from "../store/customer-list-page.actions";
import {State} from "../../../app.reducers";

@Component({
    selector: "educama-customer-list-page",
    templateUrl: "./customer-list-page.component.html"
})
export class CustomerListPageComponent implements OnDestroy {

    constructor(private _store: Store<State>,
                private _router: Router) {
    }

    public ngOnDestroy() {
        this._store.dispatch(new InitializeCustomerListAction());
    }

    /*
     * Navigate to the customer capture page
     */
    public onButtonNew(): void {
        this._router.navigate(["/customers/capture"]);
    }

    /*
     * Refresh the task list by re-loading the tasks from the server
     */
    public onButtonRefresh(): void {
        this._store.dispatch(new RequestCustomersAction());
    }

}
