import {Action, ActionReducer} from "@ngrx/store";
import {CHANGE_CUSTOMERS_PAGE, UPDATE_CUSTOMER_LIST_PAGE} from "./customer-list-page.actions";
import {CustomerResource} from "../api/resources/customer.resource";

export interface CustomerListSlice {
    pageNumber: number;
    pageSize: number;
    totalPages: number;
    customerList: CustomerResource[];
}

export const CUSTOMER_LIST_SLICE_INITIAL_STATE = {
    pageNumber: 0,
    pageSize: 5,
    totalPages: 0,
    customerList: new Array<CustomerResource>()
};

export const CUSTOMER_LIST_PAGE_REDUCER: ActionReducer<CustomerListSlice> = (state: CustomerListSlice = CUSTOMER_LIST_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {
        case CHANGE_CUSTOMERS_PAGE:
            return Object.assign({}, state, {
                pageSize: action.payload.pageSize,
                pageNumber: action.payload.pageNumber
            });
        case UPDATE_CUSTOMER_LIST_PAGE:
            return Object.assign({}, state, {
                customerList: action.payload.customers,
                totalPages: action.payload.totalPages
            });
        default:
            return state;
    }
};