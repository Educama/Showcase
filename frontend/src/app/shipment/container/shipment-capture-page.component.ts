import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ErrorService} from "../../common/error/services/error.service";
import {ShipmentService} from "../api/shipment.service";
import {ShipmentResource} from "../api/resources/shipment.resource";
import {CreateShipmentEvent} from "../components/shipment-capture.component";

@Component({
    selector: 'educama-shipment-capture-page',
    templateUrl: './shipment-capture-page.component.html'
})
export class ShipmentCapturePageComponent {

    constructor(private _errorService: ErrorService,
                private _router: Router,
                private _shipmentService: ShipmentService) {
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the creation of a new shipment
     */
    public onCreateShipmentEvent(createShipmentEvent: CreateShipmentEvent) {
        console.log("onCreateShipmentEvent");
        let shipment = new ShipmentResource();
        shipment.customer = createShipmentEvent.customer;
        shipment.receiverAddress = createShipmentEvent.receiverAddress;
        shipment.senderAddress = createShipmentEvent.senderAddress;
        this._shipmentService.createShipment(shipment)
            .subscribe(shipment => {
                this._router.navigate(['/home']);
            })
    }

    /*
     * Handles the cancellation of a new shipment creation
     */
    public onCreateShipmentCancellationEvent() {
        this._router.navigate(['/home']);
    }

    /*
     * Handles the error events from components
     */
    public onErrorEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
    }

}
