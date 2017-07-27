import {Component, OnDestroy, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import * as actions from "../../shipment-common/store/shipments/shipment-list-page.actions";
import {ShipmentService} from "../../shipment-common/api/shipment.service";
import {ShipmentListModel, ShipmentListRowModel} from "./shipment-list-page.model";
import {State} from "../../../app.reducers";
import {ShipmentResource} from "../../shipment-common/api/resources/shipment.resource";
import {Address} from "../../../customer/customer-common/api/datastructures/address.datastructure";
import {ShipmentListSlice} from "../../shipment-common/store/shipments/shipment-list-page.slice";

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

    constructor(private _router: Router,
                private _shipmentService: ShipmentService,
                private _store: Store<State>) {

        this.shipmentListSlice = this._store.select(state => state.shipmentListSlice);
        this.shipmentListSliceSubscription = this.shipmentListSlice
            .subscribe(shipmentListSlice => this.updateShipmentListModel(shipmentListSlice));
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
        this.shipmentListModel.shipmentList =
            shipmentListSlice.shipmentList.map(
                shipmentResource => new ShipmentListRowModel(
                    shipmentResource.trackingId,
                    this.formatAddress(shipmentResource.sender.address),
                    this.formatAddress(shipmentResource.receiver.address))
            );
    }
    private formatAddress(address: Address): string {
        let formatedAddress = "";
        formatedAddress += address.street + " ";
        formatedAddress += address.streetNo + ", ";
        formatedAddress += address.zipCode + " ";
        formatedAddress += address.city;
        return formatedAddress;
    }
}
