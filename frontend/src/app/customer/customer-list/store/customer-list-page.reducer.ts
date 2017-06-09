import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./customer-list-page.actions";
import {CUSTOMER_LIST_SLICE_INITIAL_STATE} from "./customer-list-page.initial-state";
import {CustomerListSlice} from "./customer-list-page.slice";

export const CUSTOMER_LIST_PAGE_REDUCER: ActionReducer<CustomerListSlice> = (state: CustomerListSlice = CUSTOMER_LIST_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {

        case actions.INITIALIZE_CUSTOMER_LIST:
            return CUSTOMER_LIST_SLICE_INITIAL_STATE;

        case actions.CHANGE_CUSTOMERS_PAGE:
            return Object.assign({}, state, {
                pageSize: action.payload.pageSize,
                pageNumber: action.payload.pageNumber
            });

        case actions.REQUEST_CUSTOMERS:
            return Object.assign({}, state, {
                loading: true
            });

        case actions.REQUEST_CUSTOMERS_SUCCESSFUL:
            return Object.assign({}, state, {
                customerList: action.payload.customers,
                totalPages: action.payload.totalPages,
                loading: false
            });

        case actions.REQUEST_CUSTOMERS_FAILED:
            return Object.assign({}, state, {
                loading: false
            });

        default:
            return state;
    }
};