import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ErrorService} from "../../common/error/services/error.service";
import {CustomerService} from "../api/customer.service";
import {CustomerResource} from "../api/resources/customer.resource";
import {CreateCustomerEvent} from "../components/customer-capture.component";

@Component({
    selector: "educama-customer-capture-page",
    templateUrl: "customer-capture-page.component.html"
})
export class CustomerCapturePageComponent {

    constructor(private _errorService: ErrorService,
                private _router: Router,
                private _customerService: CustomerService) {
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the creation of a new customer
     */
    public onCreateCustomerEvent(createCustomerEvent: CreateCustomerEvent) {
        console.log("onCreateCustomerEvent");
        let customer = new CustomerResource();
        customer.name = createCustomerEvent.name;
        customer.address = createCustomerEvent.address;

        this._customerService.createCustomer(customer)
            .subscribe(customer => {
                this._router.navigate(["/customers"]);
            })
    }

    /*
     * Handles the cancellation of a new customer creation
     */
    public onCreateCustomerCancellationEvent() {
        this._router.navigate(["/customers"]);
    }

    /*
     * Handles the error events from components
     */
    public onErrorEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
    }

}
