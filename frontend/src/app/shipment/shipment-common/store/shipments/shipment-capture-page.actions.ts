import {Action} from "@ngrx/store";
import {ShipmentResource} from "../../api/resources/shipment.resource";
export const LOAD_SHIPMENT = "LOAD_SHIPMENT";
export const RESET_SHIPMENT_CAPTURE_SLICE = "type = RESET_SHIPMENT_CAPTURE_SLICE";

export class LoadShipmentAction implements Action {
    type = LOAD_SHIPMENT;

    constructor(public payload: ShipmentResource) {

    }
}

export class ResetShipmentCaptureSliceAction implements Action {
    type = RESET_SHIPMENT_CAPTURE_SLICE;

    constructor(public payload: any) {

    }
}
