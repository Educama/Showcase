import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./customer-capture-page.actions";
import {CustomerResource} from "../api/resources/customer.resource";

export interface CustomerCaptureSlice {
    customer?: CustomerResource;
}

export const CUSTOMER_CAPTURE_SLICE_INITIAL_STATE = {
};

export const CUSTOMER_CAPTURE_PAGE_REDUCER: ActionReducer<CustomerCaptureSlice> = (state: CustomerCaptureSlice = CUSTOMER_CAPTURE_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {

        case actions.LOAD_CUSTOMER:
            return Object.assign({}, state, {
                customer: action.payload
            });
        case actions.RESET_CUSTOMER_CAPTURE_SLICE:
            return CUSTOMER_CAPTURE_SLICE_INITIAL_STATE;
        default:
            return state;
    }
};