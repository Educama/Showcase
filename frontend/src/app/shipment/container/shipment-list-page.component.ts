import {Component, OnInit, OnDestroy} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import * as actions from "../reducer/shipment-list-page.actions";
import {ErrorService} from "../../common/error/services/error.service";
import {ShipmentService} from "../api/shipment.service";
import {ShipmentListSlice} from "../reducer/shipment-list-page.reducer";
import {ShipmentListModel} from "./shipment-list-page.model";
import {State} from "../../app.reducers";
import {ShipmentResource} from "../api/resources/shipment.resource";
import {CustomerListPageModel} from "../../customer/container/customer-list-page.model";
import {CustomerListSlice} from "../../customer/reducer/customer-list-page.reducer";
import {CustomerListResource} from "../../customer/api/resources/customer-list.resource";

@Component({
    selector: "educama-shipment-list-page",
    templateUrl: "./shipment-list-page.component.html"
})
export class ShipmentListPageComponent implements OnInit, OnDestroy {

    // relevant slice of store and subscription for this slice
    public shipmentListSlice: Observable<ShipmentListSlice>;
    public shipmentListSliceSubscription: Subscription;

    // model for the page
    public shipmentListModel: ShipmentListModel = new ShipmentListModel();

    public selectedShipment: ShipmentResource = new ShipmentResource();

    constructor(private _errorService: ErrorService,
                private _router: Router,
                private _shipmentService: ShipmentService,
                private _store: Store<State>) {

        this.shipmentListSlice = this._store.select(state => state.shipmentListSlice);
        this.shipmentListSliceSubscription = this.shipmentListSlice
            .subscribe(shipmentListSlice => this.updateShipmentListModel(shipmentListSlice))
    }

    public ngOnInit() {
        this.loadShipments();
    }

    public ngOnDestroy() {
        this.shipmentListSliceSubscription.unsubscribe();
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the error events from components
     */
    public onErrorEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
    }

    /*
     * Navigate to the shipment capture page
     */
    public onButtonNew(): void {
        this._router.navigate(["/shipments/capture"]);
    }

    /*
     * Refresh the task list by re-loading the tasks from the server
     */
    public onButtonRefresh(): void {
        this.loadShipments();
    }

    // ***************************************************
    // Data Retrieval
    // ***************************************************

    private loadShipments() {
        this._shipmentService.findShipments()
            .subscribe(
                shipmentListResource => this._store.dispatch(new actions.LoadShipmentsAction(shipmentListResource.shipments)),
                null
            );
    }

    private updateShipmentListModel(shipmentListSlice: ShipmentListSlice) {
        this.shipmentListModel.shipmentList = shipmentListSlice.shipmentList;
    }
}
