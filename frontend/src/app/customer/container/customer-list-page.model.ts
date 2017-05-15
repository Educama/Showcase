import {CustomerResource} from "../api/resources/customer.resource";

export class CustomerListPageModel {
    pageSize: number;
    pageNumber: number;
    totalPages: number;
    public customerList: CustomerResource[];
}

export class LoadCustomersTrigger {
    pageSize: number;
    pageNumber: number;

    constructor(pageSize: number, pageNumber: number) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}