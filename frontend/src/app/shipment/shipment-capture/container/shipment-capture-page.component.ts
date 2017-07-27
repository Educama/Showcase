import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {State} from "../../../app.reducers";
import {Store} from "@ngrx/store";
import {SaveShipmentEvent} from "../presentationals/events/save-shipment.event";
import {ShipmentResource} from "../../shipment-common/api/resources/shipment.resource";
import {Subscription, Observable} from "rxjs";
import {ShipmentService} from "../../shipment-common/api/shipment.service";
import {ShipmentCapturePageModel} from "./shipment-capture-page.model";
import * as actions from "../../shipment-common/store/shipments/shipment-capture-page.actions";
import * as _ from "lodash";
import {ShipmentCaptureSlice} from "../../shipment-common/store/shipments/shipment-capture-page.slice";

@Component({
    selector: "educama-shipment-capture-page",
    templateUrl: "./shipment-capture-page.component.html",
})
export class ShipmentCapturePageComponent implements OnInit, OnDestroy {

    // relevant slice of store and subscription for this slice
    public shipmentCaptureSlice: Observable<ShipmentCaptureSlice>;
    public shipmentCaptureSliceSubscription: Subscription;

    // model for the page
    public shipmentCaptureModel: ShipmentCapturePageModel = new ShipmentCapturePageModel();

    constructor(private _activatedRoute: ActivatedRoute,
                private _shipmentService: ShipmentService,
                private _router: Router,
                private _store: Store<State>) {

        this.shipmentCaptureSlice = this._store.select(state => state.shipmentCaptureSlice);

        this.shipmentCaptureSliceSubscription = this.shipmentCaptureSlice.subscribe(
            shipmentCaptureSlice => this.updateShipmentCaptureModel(shipmentCaptureSlice)
        );
    }

    public ngOnInit() {
        this._activatedRoute.params.subscribe(params => {
            if (params["id"] && params["id"] !== "capture") {
                this.loadShipment(params["id"]);
            }
            console.log(params["id"]);
        });
    }

    public ngOnDestroy() {
        this._store.dispatch(new actions.ResetShipmentCaptureSliceAction(""));
        this.shipmentCaptureSliceSubscription.unsubscribe();
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the save event for a shipment
     */
    public onSaveShipmentEvent(saveShipmentEvent: SaveShipmentEvent) {
        if (_.isUndefined(this.shipmentCaptureModel.shipment)) {
            this._shipmentService.createShipment(this.mapShipmentFromSaveShipmentEvent(saveShipmentEvent))
                .subscribe(shipment => {
                    this._router.navigate(["/shipments"]);
                });
        } else {
            this._activatedRoute.params.subscribe(params => {

                saveShipmentEvent.trackingId = params["id"];
                this._shipmentService.updateShipment(saveShipmentEvent.trackingId, this.mapShipmentFromSaveShipmentEvent(saveShipmentEvent))
                    .subscribe(shipment => {
                        this._router.navigate(["/shipments"]);
                    });

            });
        }
    }

    /*
     * Handles the cancellation of a new shipment creation
     */
    public onSaveShipmentCancellationEvent(saveShipmentEvent: SaveShipmentEvent) {
        this._router.navigate(["/shipments"]);
    }

    // ***************************************************
    // Data Retrieval
    // ***************************************************

    private loadShipment(trackingId: string) {
        this._shipmentService.findShipmentbyId(trackingId).subscribe(
            shipment => {
                this._store.dispatch(new actions.LoadShipmentAction(shipment));
            }
        );
        console.log(trackingId);

    }

    private updateShipmentCaptureModel(shipmentCaptureSlice: ShipmentCaptureSlice) {
        this.shipmentCaptureModel.shipment = shipmentCaptureSlice.shipment;
    }

    private mapShipmentFromSaveShipmentEvent(saveShipmentEvent: SaveShipmentEvent): ShipmentResource {
        const shipment = new ShipmentResource();
        shipment.uuidSender = saveShipmentEvent.uuidSender;
        shipment.uuidReceiver = saveShipmentEvent.uuidReceiver;
        shipment.customerTypeEnum = saveShipmentEvent.customerTypeEnum;
        shipment.shipmentCargo = saveShipmentEvent.shipmentCargo;
        shipment.shipmentServices = saveShipmentEvent.shipmentServices;
        shipment.trackingId = saveShipmentEvent.trackingId;
        return shipment;
    }

}
