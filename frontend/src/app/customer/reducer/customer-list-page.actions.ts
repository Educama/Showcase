import {Action} from "@ngrx/store";
import {CustomerListResource} from "../api/resources/customer-list.resource";

export const CHANGE_CUSTOMERS_PAGE = "CHANGE_CUSTOMERS_PAGE";
export const UPDATE_CUSTOMER_LIST_PAGE = "UPDATE_CUSTOMERS_LIST_PAGE_ACTION";

export class ChangeCustomersPageAction implements Action {
    type = CHANGE_CUSTOMERS_PAGE;

    constructor(public payload: {pageNumber: number, pageSize: number}) {
    }
}

export class UpdateCustomerListPageAction implements Action {
    type = UPDATE_CUSTOMER_LIST_PAGE;

    constructor(public payload: CustomerListResource) {

    }
}