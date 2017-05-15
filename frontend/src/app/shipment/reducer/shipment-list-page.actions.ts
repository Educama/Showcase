import {Action} from "@ngrx/store";
import {ShipmentResource} from "../api/resources/shipment.resource";

// Action Types
export const LOAD_SHIPMENTS = "LOAD_SHIPMENTS";

// Actions
export class LoadShipmentsAction implements Action {
    type = LOAD_SHIPMENTS;

    constructor(public payload: ShipmentResource[]) {
    }
}