import {ActionReducer, combineReducers} from "@ngrx/store";
import {
    ShipmentListSlice,
    SHIPMENT_LIST_SLICE_INITIAL_STATE,
    SHIPMENT_LIST_PAGE_REDUCER
} from "./shipment/reducer/shipment-list-page.reducer";
import {
    TASK_LIST_PAGE_REDUCER,
    TASK_LIST_SLICE_INITIAL_STATE,
    TaskListSlice
}
from "./shipment/reducer/task-list-page.reducer";
import {
    CustomerListSlice,
    CUSTOMER_LIST_SLICE_INITIAL_STATE,
    CUSTOMER_LIST_PAGE_REDUCER
} from "./customer/reducer/customer-list-page.reducer";
import {
    CUSTOMER_CAPTURE_SLICE_INITIAL_STATE,
    CUSTOMER_CAPTURE_PAGE_REDUCER, CustomerCaptureSlice
} from "./customer/reducer/customer-capture-page.reducer";

export interface State {
    shipmentListSlice: ShipmentListSlice;
    taskListSlice: TaskListSlice;
    customerListSlice: CustomerListSlice;
    customerCaptureSlice: CustomerCaptureSlice;
}
export const INITIAL_STATE = {
    shipmentListSlice: SHIPMENT_LIST_SLICE_INITIAL_STATE,
    taskListSlice: TASK_LIST_SLICE_INITIAL_STATE,
    customerListSlice: CUSTOMER_LIST_SLICE_INITIAL_STATE,
    customerCaptureSlice: CUSTOMER_CAPTURE_SLICE_INITIAL_STATE
};

const reducers = {
    shipmentListSlice: SHIPMENT_LIST_PAGE_REDUCER,
    taskListSlice: TASK_LIST_PAGE_REDUCER,
    customerListSlice: CUSTOMER_LIST_PAGE_REDUCER,
    customerCaptureSlice: CUSTOMER_CAPTURE_PAGE_REDUCER
};
export const REDUCER: ActionReducer<State> = combineReducers(reducers);
