import {CustomerResource} from "../../api/resources/customer.resource";
import {CustomerListSlice} from "./customer-list-page.slice";

export const CUSTOMER_LIST_SLICE_INITIAL_STATE: CustomerListSlice = {
    pageNumber: 0,
    pageSize: 5,
    totalPages: 0,
    customerList: new Array<CustomerResource>(),
    loading: false
};