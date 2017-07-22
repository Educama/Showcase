import {Action} from "@ngrx/store";
import {ShipmentResource} from "../api/resources/shipment.resource";

// Action Types
export const LOAD_SHIPMENTS = "LOAD_SHIPMENTS";
export const LOAD_SINGLE_SHIPMENT = "LOAD_SINGLE_SHIPMENT";

// Actions
export class LoadShipmentsAction implements Action {
    type = LOAD_SHIPMENTS;

    constructor(public payload: ShipmentResource[]) {
    }
}

export class LoadSingleShipmentAction implements Action {
    type = LOAD_SINGLE_SHIPMENT;

    constructor(public payload: ShipmentResource) {
    }
}
