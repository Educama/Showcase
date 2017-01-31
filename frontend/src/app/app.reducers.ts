import {ActionReducer, combineReducers} from "@ngrx/store";
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
export interface State {
    shipmentListSlice: ShipmentListSlice;
    taskListSlice: TaskListSlice;
}
export const INITIAL_STATE = {
    shipmentListSlice: SHIPMENT_LIST_SLICE_INITIAL_STATE,
    taskListSlice: TASK_LIST_SLICE_INITIAL_STATE
};

const reducers = {
    shipmentListSlice: SHIPMENT_LIST_PAGE_REDUCER,
    taskListSlice: TASK_LIST_PAGE_REDUCER
};
export const REDUCER: ActionReducer<State> = combineReducers(reducers);
