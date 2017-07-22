import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./shipment-list-page.actions";
import {ShipmentResource} from "../api/resources/shipment.resource";

export interface ShipmentListSlice {
    shipmentList: ShipmentResource[];
}

export const SHIPMENT_LIST_SLICE_INITIAL_STATE: ShipmentListSlice = {
    shipmentList: []
};

export function shipmentListPageReducer(state: ShipmentListSlice = SHIPMENT_LIST_SLICE_INITIAL_STATE,
                                        action: Action): ShipmentListSlice {
  switch (action.type) {
    case actions.LOAD_SHIPMENTS:
      const loadShipment = action as actions.LoadShipmentsAction;
      return Object.assign({}, state, {
        shipmentList: loadShipment.payload
      });
    case actions.LOAD_SINGLE_SHIPMENT:
      const loadSingleShipmentAction = action as actions.LoadSingleShipmentAction;
      return Object.assign({}, state, {
        shipment: loadSingleShipmentAction.payload
      });
    default:
      return state;
  }
}

export const SHIPMENT_LIST_PAGE_REDUCER: ActionReducer<ShipmentListSlice> = shipmentListPageReducer;
