import {Injectable} from "@angular/core";
import {RestClientService} from "../../common/http/services/rest-client.service";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";
import {CustomerListResource} from "./resources/customer-list.resource";
import {CustomerResource} from "./resources/customer.resource";
import {CustomerSuggestionsResource} from "./resources/customer-suggestions.resource";

/*
 * Service to communicate with Customer Resource
 */
@Injectable()
export class CustomerService {

    // private CUSTOMER_RESOURCE_PATH:string = "customers";
    private CUSTOMER_RESOURCE_PATH:string = "customers";
    private CUSTOMER_SUGGESTIONS_RESOURCE_PATH:string = this.CUSTOMER_RESOURCE_PATH + "/suggestions";

    constructor(private _restClientService: RestClientService) {
    }

    /*
     * Create a new customer
     *
     * @param customer The customer to be created
     * @return An observable of a customer
     */
    public createCustomer(customer: CustomerResource): Observable<CustomerResource> {
        return this._restClientService.post(this.CUSTOMER_RESOURCE_PATH, JSON.stringify(customer));
    }

    /*
     * Update a customer
     *
     * @param customer The customer to be created
     * @return An observable of a customer
     */
    public updateCustomer(uuid: String, customer: CustomerResource): Observable<CustomerResource> {
        return this._restClientService.put(this.CUSTOMER_RESOURCE_PATH + "/" + uuid, JSON.stringify(customer));
    }

    /*
     * Find customer by
     *
     * @return An observable array of customers
     */
    public findCustomerById(uuid: string): Observable<CustomerResource> {
        return this._restClientService.get(this.CUSTOMER_RESOURCE_PATH + "/" + uuid);
    }

    /*
     * Find all customers
     *
     * @return An observable array of customers
     */
    public findCustomers(pageNumber: number, pageSize: number): Observable<CustomerListResource> {
        let pageNumberText = "page=" + pageNumber;
        let pageSizeText = "size=" + pageSize;
        return this._restClientService.get(this.CUSTOMER_RESOURCE_PATH + "?" + pageNumberText + "&" + pageSizeText);
    }

    /*
     * Find suggestions for customers based on a search term
     *
     * @return An observable array of customers
     */
    public findCustomerSuggestions(term: string, pageNumber?: number, pageSize?: number): Observable<CustomerSuggestionsResource> {
        let pageNumberText = (pageNumber) ? "&page=" + pageNumber : "&page=0";
        let pageSizeText = (pageSize) ? "&size=" + pageSize : "&size=10";
        return this._restClientService.get(this.CUSTOMER_SUGGESTIONS_RESOURCE_PATH + "?term=" + term + pageNumberText + pageSizeText);
    }

}
