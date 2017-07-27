import {CustomerResource} from "../api/resources/customer.resource";

export interface CustomerCaptureSlice {
    customer?: CustomerResource;
    saving: boolean;
}
