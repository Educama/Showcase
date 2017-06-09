import {Action} from "@ngrx/store";
import {CustomerListResource} from "../../api/resources/customer-list.resource";

// Initialize Actions
export const INITIALIZE_CUSTOMER_LIST = "INITIALIZE_CUSTOMER_LIST";

// Customer List Actions
export const CHANGE_CUSTOMERS_PAGE = "CHANGE_CUSTOMERS_PAGE";

// Customer API Actions
export const REQUEST_CUSTOMERS = "REQUEST_CUSTOMERS";
export const REQUEST_CUSTOMERS_SUCCESSFUL = "REQUEST_CUSTOMERS_SUCCESSFUL";
export const REQUEST_CUSTOMERS_FAILED = "REQUEST_CUSTOMERS_FAILED";

export class InitializeCustomerListAction implements Action {
    type = INITIALIZE_CUSTOMER_LIST;

    constructor() {
    }
}

export class ChangeCustomersPageAction implements Action {
    type = CHANGE_CUSTOMERS_PAGE;

    constructor(public payload: { pageNumber: number, pageSize: number }) {
    }
}

export class RequestCustomersAction implements Action {
    type = REQUEST_CUSTOMERS;

    constructor() {
    }
}

export class RequestCustomersSuccessfulAction implements Action {
    type = REQUEST_CUSTOMERS_SUCCESSFUL;

    constructor(public payload: CustomerListResource) {
    }
}

export class RequestCustomersFailedAction implements Action {
    type = REQUEST_CUSTOMERS_FAILED;

    constructor() {
    }
}
