import {Component, OnInit, OnDestroy} from "@angular/core";
import {Router, ActivatedRoute} from "@angular/router";
import {Store} from "@ngrx/store";
import {ErrorService} from "../../common/error/services/error.service";
import {CustomerService} from "../api/customer.service";
import {CustomerResource} from "../api/resources/customer.resource";
import {SaveCustomerEvent} from "../components/events/save-customer.event";
import {State} from "../../app.reducers";
import * as actions from "../reducer/customer-capture-page.actions";
import {Observable, Subscription} from "rxjs";
import {CustomerCaptureSlice} from "../reducer/customer-capture-page.reducer";
import {CustomerCapturePageModel} from "./customer-capture-page.model";
import * as _ from "lodash";

@Component({
    selector: "educama-customer-capture-page",
    templateUrl: "customer-capture-page.component.html"
})
export class CustomerCapturePageComponent implements OnInit, OnDestroy {

    // relevant slice of store and subscription for this slice
    public customerCaptureSlice: Observable<CustomerCaptureSlice>;
    public customerCaptureSliceSubscription: Subscription;

    // model for the page
    public customerCaptureModel: CustomerCapturePageModel = new CustomerCapturePageModel();

    constructor(private _activatedRoute: ActivatedRoute,
                private _errorService: ErrorService,
                private _customerService: CustomerService,
                private _router: Router,
                private _store: Store<State>) {

        this.customerCaptureSlice = this._store.select(state => state.customerCaptureSlice);

        this.customerCaptureSliceSubscription = this.customerCaptureSlice.subscribe(
            customerCaptureSlice => this.updateCustomerCaptureModel(customerCaptureSlice)
        );
    }

    public ngOnInit() {
        this._activatedRoute.params.subscribe(params => {
            if (params["id"] && params["id"] !== "capture") {
                this.loadCustomer(params["id"]);
            }
        });
    }

    public ngOnDestroy() {
        this._store.dispatch(new actions.ResetCustomerCaptureSliceAction(""));
        this.customerCaptureSliceSubscription.unsubscribe();
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the save event for a customer
     */
    public onSaveCustomerEvent(saveCustomerEvent: SaveCustomerEvent) {
        if (_.isUndefined(this.customerCaptureModel.customer)) {
            this._customerService.createCustomer(this.mapCustomerFromSaveCustomerEvent(saveCustomerEvent))
                .subscribe(customer => {
                    this._router.navigate(["/customers"]);
                })
        }
        else {
            this._customerService.updateCustomer(saveCustomerEvent.uuid, this.mapCustomerFromSaveCustomerEvent(saveCustomerEvent))
                .subscribe(customer => {
                    this._router.navigate(["/customers"]);
                })
        }
    }

    /*
     * Handles the cancellation of a new customer creation
     */
    public onSaveCustomerCancellationEvent(saveCustomerEvent: SaveCustomerEvent) {
        this._router.navigate(["/customers"]);
    }

    /*
     * Handles the error events from components
     */
    public onSaveEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
    }

    // ***************************************************
    // Data Retrieval
    // ***************************************************

    private loadCustomer(uuid: string) {
        this._customerService.findCustomerById(uuid).subscribe(
            customer => {
                this._store.dispatch(new actions.LoadCustomerAction(customer));
            }
        );
    }

    private updateCustomerCaptureModel(customerCaptureSlice: CustomerCaptureSlice) {
        this.customerCaptureModel.customer = customerCaptureSlice.customer;
    }

    private mapCustomerFromSaveCustomerEvent(saveCustomerEvent: SaveCustomerEvent): CustomerResource {
        let customer = new CustomerResource();
        customer.version = saveCustomerEvent.version;
        customer.name = saveCustomerEvent.name;
        customer.address = saveCustomerEvent.address;
        return customer;
    }

}
