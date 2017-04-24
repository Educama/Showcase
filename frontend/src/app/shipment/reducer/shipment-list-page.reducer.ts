import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./shipment-list-page.actions";
import {ShipmentResource} from "../api/resources/shipment.resource";

export interface ShipmentListSlice {
    shipmentList: ShipmentResource[],
}

export const SHIPMENT_LIST_SLICE_INITIAL_STATE: ShipmentListSlice = {
    shipmentList: []
};

export const SHIPMENT_LIST_PAGE_REDUCER: ActionReducer<ShipmentListSlice> =
    (state: ShipmentListSlice = SHIPMENT_LIST_SLICE_INITIAL_STATE, action: Action) => {
        switch (action.type) {
            case actions.LOAD_SHIPMENTS:
                return Object.assign({}, state, {
                    shipmentList: action.payload
                });
            default:
                return state;
    }
};