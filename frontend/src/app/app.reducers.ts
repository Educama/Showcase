import {ActionReducerMap} from "@ngrx/store";
import {SHIPMENT_LIST_PAGE_REDUCER} from "./shipment/shipment-common/store/shipments/shipment-list-page.reducer";
import {TASK_LIST_PAGE_REDUCER} from "./shipment/shipment-common/store/tasks/task-list-page.reducer";
import {CUSTOMER_LIST_PAGE_REDUCER} from "./customer/customer-common/store/customer-list-page.reducer";
import {CUSTOMER_CAPTURE_PAGE_REDUCER} from "./customer/customer-common/store/customer-capture-page.reducer";
import {CustomerListSlice} from "./customer/customer-common/store/customer-list-page.slice";
import {CUSTOMER_LIST_SLICE_INITIAL_STATE} from "./customer/customer-common/store/customer-list-page.initial-state";
import {CustomerCaptureSlice} from "./customer/customer-common/store/customer-capture-page.slice";
import {CUSTOMER_CAPTURE_SLICE_INITIAL_STATE} from "./customer/customer-common/store/customer-capture-page.initial-state";
import {ERROR_REDUCER} from "./common/error/store/error.reducer";
import {ERROR_SLICE_INITIAL_STATE} from "./common/error/store/error.initial-state";
import {ErrorSlice} from "./common/error/store/error.slice";
import {SHIPMENT_CAPTURE_PAGE_REDUCER} from "./shipment/shipment-common/store/shipments/shipment-capture-page.reducer";
import {AirportListSlice} from "./flights/flights-common/store/airport-list-page.slice";
import {AIRPORT_LIST_SLICE_INITIAL_STATE} from "./flights/flights-common/store/airport-list-page.initial-state";
import {AIRPORT_LIST_PAGE_REDUCER} from "./flights/flights-common/store/airport-list-page.reducer";
import {ShipmentListSlice} from "./shipment/shipment-common/store/shipments/shipment-list-page.slice";
import {TaskListSlice} from "./shipment/shipment-common/store/tasks/task-list-page.slice";
import {ShipmentCaptureSlice} from "./shipment/shipment-common/store/shipments/shipment-capture-page.slice";
import {TASK_LIST_SLICE_INITIAL_STATE} from "./shipment/shipment-common/store/tasks/task-list-page.initial-state";
import {SHIPMENT_LIST_SLICE_INITIAL_STATE} from "./shipment/shipment-common/store/shipments/shipment-list-page.initial-state";
import {SHIPMENT_CAPTURE_SLICE_INITIAL_STATE} from "./shipment/shipment-common/store/shipments/shipment-capture-page.initial-state";

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
