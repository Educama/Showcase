import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./customer-list-page.actions";
import {CUSTOMER_LIST_SLICE_INITIAL_STATE} from "./customer-list-page.initial-state";
import {CustomerListSlice} from "./customer-list-page.slice";
import {ChangeCustomersPageAction, RequestCustomersSuccessfulAction} from "./customer-list-page.actions";

export function customerListPageReducer(state: CustomerListSlice = CUSTOMER_LIST_SLICE_INITIAL_STATE, action: Action): CustomerListSlice {
    switch (action.type) {

        case actions.INITIALIZE_CUSTOMER_LIST:
            return CUSTOMER_LIST_SLICE_INITIAL_STATE;

        case actions.CHANGE_CUSTOMERS_PAGE:
            const changeCustomersPageAction = action as ChangeCustomersPageAction;
            return Object.assign({}, state, {
                pageSize: changeCustomersPageAction.payload.pageSize,
                pageNumber: changeCustomersPageAction.payload.pageNumber
            });

        case actions.REQUEST_CUSTOMERS:
            return Object.assign({}, state, {
                loading: true
            });

        case actions.REQUEST_CUSTOMERS_SUCCESSFUL:
            const requestCustomersSuccessfulAction = action as RequestCustomersSuccessfulAction;
            return Object.assign({}, state, {
                customerList: requestCustomersSuccessfulAction.payload.customers,
                totalPages: requestCustomersSuccessfulAction.payload.totalPages,
                loading: false
            });

        case actions.REQUEST_CUSTOMERS_FAILED:
            return Object.assign({}, state, {
                loading: false
            });

        default:
            return state;
    }
}

export const CUSTOMER_LIST_PAGE_REDUCER: ActionReducer<CustomerListSlice> = customerListPageReducer;
