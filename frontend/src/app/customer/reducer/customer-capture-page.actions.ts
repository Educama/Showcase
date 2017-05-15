import {Action} from "@ngrx/store";
import {CustomerListResource} from "../api/resources/customer-list.resource";
import {CustomerResource} from "../api/resources/customer.resource";

export const LOAD_CUSTOMER = "LOAD_CUSTOMER";
export const RESET_CUSTOMER_CAPTURE_SLICE = "type = RESET_CUSTOMER_CAPTURE_SLICE";

export class LoadCustomerAction implements Action {
    type = LOAD_CUSTOMER;

    constructor(public payload: CustomerResource) {

    }
}

export class ResetCustomerCaptureSliceAction implements Action {
    type = RESET_CUSTOMER_CAPTURE_SLICE;

    constructor(public payload: any) {

    }
}