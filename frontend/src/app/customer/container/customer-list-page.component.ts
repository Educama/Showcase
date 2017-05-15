import {Component, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import "rxjs/add/operator/distinctUntilChanged";
import {LazyLoadEvent} from "primeng/components/common/api";
import * as _ from  "lodash";

import * as actions from "../reducer/customer-list-page.actions";
import {ErrorService} from "../../common/error/services/error.service";
import {CustomerService} from "../api/customer.service";
import {CustomerListSlice} from "../reducer/customer-list-page.reducer";
import {CustomerListPageModel, LoadCustomersTrigger} from "./customer-list-page.model";
import {State} from "../../app.reducers";
import {CustomerResource} from "../api/resources/customer.resource";

@Component({
    selector: "educama-customer-list-page",
    templateUrl: "customer-list-page.component.html"
})
export class CustomerListPageComponent implements OnDestroy {

    // relevant slice of store and subscription for this slice
    public customerListSlice: Observable<CustomerListSlice>;
    public customerListSliceSubscription: Subscription;

    public loadCustomersTrigger: Observable<LoadCustomersTrigger>;
    public loadCustomersTriggerSubscription: Subscription;

    // model for the page
    public customerListModel: CustomerListPageModel = new CustomerListPageModel();

    public selectedCustomer: CustomerResource = new CustomerResource();

    constructor(private _errorService: ErrorService,
                private _router: Router,
                private _customerService: CustomerService,
                private _store: Store<State>) {

        this.customerListSlice = this._store.select(state => state.customerListSlice);

        this.customerListSliceSubscription = this.customerListSlice.subscribe(
            customerListSlice => this.updateCustomerListModel(customerListSlice)
        );

        this.loadCustomersTrigger = this._store
            .select(state => new LoadCustomersTrigger(
                state.customerListSlice.pageSize,
                state.customerListSlice.pageNumber
            ))
            .distinctUntilChanged(function (a, b) {
                return _.isEqual(a, b)
            });

        this.loadCustomersTriggerSubscription = this.loadCustomersTrigger
            .subscribe(loadCustomersTrigger => {
                this.loadCustomers(loadCustomersTrigger.pageNumber, loadCustomersTrigger.pageSize);
            });

    }

    public ngOnDestroy() {
        this.customerListSliceSubscription.unsubscribe();
        this.loadCustomersTriggerSubscription.unsubscribe()
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public onCustomerSelectedEvent(uuid: string) {
        this._router.navigate(["/customers/edit/" + uuid]);
    }

    /*
     * Handles the lazy load event from the Customer List Component
     */
    public onLoadCustomersPageEvent(event: LazyLoadEvent) {
        this._store.dispatch(new actions.ChangeCustomersPageAction(
            {pageNumber: event.first / event.rows, pageSize: event.rows}
        ));
    }

    /*
     * Handles the error events from components
     */
    public onErrorEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
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
        this.loadCustomers(this.customerListModel.pageNumber, this.customerListModel.pageSize);
    }

    // ***************************************************
    // Data Retrieval
    // ***************************************************

    private loadCustomers(pageNumber: number, pageSize: number) {
        this._customerService.findCustomers(pageNumber, pageSize)
            .subscribe(customerListResource => {
                this._store.dispatch(new actions.UpdateCustomerListPageAction(customerListResource));
            });
    }

    private updateCustomerListModel(customerListSlice: CustomerListSlice) {
        this.customerListModel.customerList = customerListSlice.customerList;
        this.customerListModel.pageNumber = customerListSlice.pageNumber;
        this.customerListModel.pageSize = customerListSlice.pageSize;
        this.customerListModel.totalPages = customerListSlice.totalPages;
    }
}
