import {ShipmentResource} from "../api/resources/shipment.resource";
import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./shipment-capture-page.actions";
export interface ShipmentCaptureSlice {
    shipment?: ShipmentResource;
}

export const SHIPMENT_CAPTURE_SLICE_INITIAL_STATE = {
};

export function shipmentCapturePageReducer(state: ShipmentCaptureSlice = SHIPMENT_CAPTURE_SLICE_INITIAL_STATE,
                                           action: Action): ShipmentCaptureSlice {
    switch (action.type) {
        case actions.LOAD_SHIPMENT:
            const loadShipment = action as actions.LoadShipmentAction;
            return Object.assign({}, state, {
                shipment: loadShipment.payload
            });
        case actions.RESET_SHIPMENT_CAPTURE_SLICE:
            return SHIPMENT_CAPTURE_SLICE_INITIAL_STATE;
        default:
            return state;
    }
}

export const SHIPMENT_CAPTURE_PAGE_REDUCER: ActionReducer<ShipmentCaptureSlice> = shipmentCapturePageReducer;
