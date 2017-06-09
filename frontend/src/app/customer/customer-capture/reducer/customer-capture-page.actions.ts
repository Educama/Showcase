import {Action} from "@ngrx/store";
import {CustomerResource} from "../../api/resources/customer.resource";

export const INITIALIZE_CUSTOMER_CAPTURE_SLICE = "INITIALIZE_CUSTOMER_CAPTURE_SLICE";
export const LOAD_CUSTOMER = "LOAD_CUSTOMER";
export const SAVE_CUSTOMER = "SAVE_CUSTOMER";

export const POST_CUSTOMER = "POST_CUSTOMER";
export const POST_CUSTOMER_SUCCESSFUL = "POST_CUSTOMER_SUCCESSFUL"
export const POST_CUSTOMER_FAILED = "POST_CUSTOMER_FAILED"

export const PUT_CUSTOMER = "PUT_CUSTOMER";
export const PUT_CUSTOMER_SUCCESSFUL = "PUT_CUSTOMER_SUCCESSFUL"
export const PUT_CUSTOMER_FAILED = "PUT_CUSTOMER_FAILED"

export class InitializeCustomerCaptureSliceAction implements Action {
    type = INITIALIZE_CUSTOMER_CAPTURE_SLICE;

    constructor() {
    }
}

export class LoadCustomerAction implements Action {
    type = LOAD_CUSTOMER;

    constructor(public payload: CustomerResource) {
    }
}

export class SaveCustomerAction implements Action {
    type = SAVE_CUSTOMER;

    constructor(public payload: CustomerResource) {
    }
}

export class PostCustomerAction implements Action {
    type = POST_CUSTOMER;

    constructor(public payload: CustomerResource) {
    }
}

export class PostCustomerSuccessfulAction implements Action {
    type = POST_CUSTOMER_SUCCESSFUL;

    constructor(public payload: CustomerResource) {
    }
}

export class PostCustomerFailedAction implements Action {
    type = POST_CUSTOMER_FAILED;

    constructor() {
    }
}

export class PutCustomerAction implements Action {
    type = PUT_CUSTOMER;

    constructor(public payload: CustomerResource) {
    }
}

export class PutCustomerSuccessfulAction implements Action {
    type = PUT_CUSTOMER_SUCCESSFUL;

    constructor(public payload: CustomerResource) {
    }
}

export class PutCustomerFailedAction implements Action {
    type = PUT_CUSTOMER_FAILED;

    constructor() {
    }
}