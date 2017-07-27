import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./shipment-list-page.actions";
import {SHIPMENT_LIST_SLICE_INITIAL_STATE} from "./shipment-list-page.initial-state";
import {ShipmentListSlice} from "./shipment-list-page.slice";

export function shipmentListPageReducer(state: ShipmentListSlice = SHIPMENT_LIST_SLICE_INITIAL_STATE,
                                        action: Action): ShipmentListSlice {
  switch (action.type) {
    case actions.INITIALIZE_SHIPMENT_LIST:
      return SHIPMENT_LIST_SLICE_INITIAL_STATE;
    case actions.REQUEST_SHIPMENTS:
      return Object.assign({}, state, {
        loading: true
      });
    case actions.REQUEST_SHIPMENTS_SUCCESSFUL:
      const requestShipmentsSuccessfulAction = action as actions.RequestShipmentsSuccessfulAction;
      return Object.assign({}, state, {
        shipmentList: requestShipmentsSuccessfulAction.payload.shipments,
        loading: false
      });
    case actions.REQUEST_SHIPMENTS_FAILED:
      return Object.assign({}, state, {
        loading: false
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
