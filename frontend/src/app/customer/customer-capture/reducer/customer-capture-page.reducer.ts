import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./customer-capture-page.actions";
import {CustomerResource} from "../../api/resources/customer.resource";
import {CUSTOMER_CAPTURE_SLICE_INITIAL_STATE} from "./customer-capture-page.initial-state";
import {CustomerCaptureSlice} from "./customer-capture-page.slice";

export const CUSTOMER_CAPTURE_PAGE_REDUCER: ActionReducer<CustomerCaptureSlice> = (state: CustomerCaptureSlice = CUSTOMER_CAPTURE_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {

        case actions.LOAD_CUSTOMER:
            return Object.assign({}, state, {
                customer: action.payload
            });

        case actions.INITIALIZE_CUSTOMER_CAPTURE_SLICE:
            return CUSTOMER_CAPTURE_SLICE_INITIAL_STATE;

        case actions.POST_CUSTOMER:
            return Object.assign({}, state, {
                saving: true
            });

        case actions.POST_CUSTOMER_SUCCESSFUL:
            return Object.assign({}, state, {
                saving: false
            });

        case actions.POST_CUSTOMER_SUCCESSFUL:
            return Object.assign({}, state, {
                saving: false
            });

        default:
            return state;
    }
};