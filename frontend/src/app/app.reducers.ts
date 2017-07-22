import {ActionReducer, ActionReducerMap, combineReducers} from "@ngrx/store";
import {
    SHIPMENT_LIST_PAGE_REDUCER,
    SHIPMENT_LIST_SLICE_INITIAL_STATE,
    ShipmentListSlice
} from "./shipment/reducer/shipment-list-page.reducer";
import {
    TASK_LIST_PAGE_REDUCER,
    TASK_LIST_SLICE_INITIAL_STATE,
    TaskListSlice
} from "./shipment/reducer/task-list-page.reducer";
import {CUSTOMER_LIST_PAGE_REDUCER} from "./customer/customer-list/store/customer-list-page.reducer";
import {CUSTOMER_CAPTURE_PAGE_REDUCER} from "./customer/customer-capture/reducer/customer-capture-page.reducer";
import {CustomerListSlice} from "./customer/customer-list/store/customer-list-page.slice";
import {CUSTOMER_LIST_SLICE_INITIAL_STATE} from "./customer/customer-list/store/customer-list-page.initial-state";
import {CustomerCaptureSlice} from "./customer/customer-capture/reducer/customer-capture-page.slice";
import {CUSTOMER_CAPTURE_SLICE_INITIAL_STATE} from "./customer/customer-capture/reducer/customer-capture-page.initial-state";
import {ERROR_REDUCER} from "./common/error/store/error.reducer";
import {ERROR_SLICE_INITIAL_STATE} from "./common/error/store/error.initial-state";
import {ErrorSlice} from "./common/error/store/error.slice";
import {
    SHIPMENT_CAPTURE_PAGE_REDUCER,
    SHIPMENT_CAPTURE_SLICE_INITIAL_STATE,
    ShipmentCaptureSlice
} from "./shipment/reducer/shipment-capture-page.reducer";
import {AirportListSlice} from "./flights/airport-list/store/airport-list-page.slice";
import {AIRPORT_LIST_SLICE_INITIAL_STATE} from "./flights/airport-list/store/airport-list-page.initial-state";
import {AIRPORT_LIST_PAGE_REDUCER} from "./flights/airport-list/store/airport-list-page.reducer";

export interface State {
    shipmentListSlice: ShipmentListSlice;
    taskListSlice: TaskListSlice;
    customerListSlice: CustomerListSlice;
    customerCaptureSlice: CustomerCaptureSlice;
    errorSlice: ErrorSlice;
    shipmentCaptureSlice: ShipmentCaptureSlice;
    airportListSlice: AirportListSlice;
}

export const INITIAL_STATE = {
    shipmentListSlice: SHIPMENT_LIST_SLICE_INITIAL_STATE,
    taskListSlice: TASK_LIST_SLICE_INITIAL_STATE,
    customerListSlice: CUSTOMER_LIST_SLICE_INITIAL_STATE,
    customerCaptureSlice: CUSTOMER_CAPTURE_SLICE_INITIAL_STATE,
    errorSlice: ERROR_SLICE_INITIAL_STATE,
    shipmentCaptureSlice: SHIPMENT_CAPTURE_SLICE_INITIAL_STATE,
    airportListSlice: AIRPORT_LIST_SLICE_INITIAL_STATE
};

export const reducers: ActionReducerMap<State> = {
    shipmentListSlice: SHIPMENT_LIST_PAGE_REDUCER,
    taskListSlice: TASK_LIST_PAGE_REDUCER,
    customerListSlice: CUSTOMER_LIST_PAGE_REDUCER,
    customerCaptureSlice: CUSTOMER_CAPTURE_PAGE_REDUCER,
    errorSlice: ERROR_REDUCER,
    shipmentCaptureSlice: SHIPMENT_CAPTURE_PAGE_REDUCER,
    airportListSlice: AIRPORT_LIST_PAGE_REDUCER
};
