import {Component, Output, OnInit, EventEmitter} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {Address} from "../api/datastructures/address.datastructure";

@Component({
    selector: "educama-customer-capture",
    templateUrl: "customer-capture.component.html"
})
export class CustomerCaptureComponent implements OnInit {

    @Output()
    public createCustomerEvent: EventEmitter<CreateCustomerEvent> = new EventEmitter<CreateCustomerEvent>();

    @Output()
    public createCustomerCancellationEvent = new EventEmitter();

    public customerCaptureForm: FormGroup;

    constructor(private _formBuilder: FormBuilder) {
    }

    public ngOnInit() {
        this.customerCaptureForm = this._formBuilder.group({
            name: ["", [Validators.required]],
            street: ["", [Validators.required]],
            housenumber: ["", [Validators.required]],
            postalcode: ["", [Validators.required]],
            city: ["", [Validators.required]]
        });
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public cancel() {
        this.customerCaptureForm.reset();
        this.createCustomerCancellationEvent.emit(null);
    }

    public createCustomer() {
        this.createCustomerEvent.emit(
            new CreateCustomerEvent(
                this.customerCaptureForm.get("name").value,
                new Address(
                    this.customerCaptureForm.get("street").value,
                    this.customerCaptureForm.get("housenumber").value,
                    this.customerCaptureForm.get("postalcode").value,
                    this.customerCaptureForm.get("city").value
                )
            )
        )
    }
}

export class CreateCustomerEvent {
    name: string;
    address: Address;

    constructor(name: string, address: Address) {
        this.name = name;
        this.address = address;
    }
}