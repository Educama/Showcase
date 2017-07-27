import {CustomerResource} from "../api/resources/customer.resource";

export interface CustomerListSlice {
    pageNumber: number;
    pageSize: number;
    totalPages: number;
    customerList: CustomerResource[];
    loading: boolean;
}
