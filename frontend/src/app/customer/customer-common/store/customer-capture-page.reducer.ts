import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./customer-capture-page.actions";
import {CUSTOMER_CAPTURE_SLICE_INITIAL_STATE} from "./customer-capture-page.initial-state";
import {CustomerCaptureSlice} from "./customer-capture-page.slice";
import {LoadCustomerAction} from "./customer-capture-page.actions";

export function customerCapturePageReducer(state: CustomerCaptureSlice = CUSTOMER_CAPTURE_SLICE_INITIAL_STATE,
                                           action: Action): CustomerCaptureSlice {
    switch (action.type) {

        case actions.LOAD_CUSTOMER:
            const loadCustomerAction = action as LoadCustomerAction;
            return Object.assign({}, state, {
                customer: loadCustomerAction.payload
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
}

export const CUSTOMER_CAPTURE_PAGE_REDUCER: ActionReducer<CustomerCaptureSlice> = customerCapturePageReducer;
