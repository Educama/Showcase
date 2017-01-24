import {ActionReducer, combineReducers} from "@ngrx/store";
import {
    ShipmentListSlice,
    SHIPMENT_LIST_SLICE_INITIAL_STATE,
    SHIPMENT_LIST_PAGE_REDUCER
} from "./shipment/reducer/shipment-list-page.reducer";
import {
    CustomerListSlice,
    CUSTOMER_LIST_SLICE_INITIAL_STATE,
    CUSTOMER_LIST_PAGE_REDUCER
} from "./customer/reducer/customer-list-page.reducer";

export interface State {
    shipmentListSlice: ShipmentListSlice;
    customerListSlice: CustomerListSlice;
}

export const INITIAL_STATE = {
    shipmentListSlice: SHIPMENT_LIST_SLICE_INITIAL_STATE,
    customerListSlice: CUSTOMER_LIST_SLICE_INITIAL_STATE
};

const reducers = {
    shipmentListSlice: SHIPMENT_LIST_PAGE_REDUCER,
    customerListSlice: CUSTOMER_LIST_PAGE_REDUCER
};

export const REDUCER: ActionReducer<State> = combineReducers(reducers);
