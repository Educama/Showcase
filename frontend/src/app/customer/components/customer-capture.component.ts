import {Component, Output, OnInit, EventEmitter, Input, DoCheck} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {Address} from "../api/datastructures/address.datastructure";
import {CustomerResource} from "../api/resources/customer.resource";
import {EditorMode} from "../../common/ui/enums/editor-mode.enum";
import {SaveCustomerEvent} from "./events/save-customer.event";

@Component({
    selector: "educama-customer-capture",
    templateUrl: "customer-capture.component.html",
})
export class CustomerCaptureComponent implements OnInit, DoCheck {

    @Input()
    public customer: CustomerResource;

    @Output()
    public saveCustomerEvent: EventEmitter<SaveCustomerEvent> = new EventEmitter<SaveCustomerEvent>();

    @Output()
    public saveCustomerCancellationEvent = new EventEmitter();

    public customerCaptureForm: FormGroup;
    public editorMode: EditorMode;

    private _isInitialized:boolean = false;

    constructor(private _formBuilder: FormBuilder) {
    }

    public ngOnInit() {

        this.customerCaptureForm = this._formBuilder.group({
            uuid: ["", []],
            name: ["", [Validators.required]],
            street: ["", [Validators.required]],
            housenumber: ["", [Validators.required]],
            postalcode: ["", [Validators.required]],
            city: ["", [Validators.required]]
        });

        // Disable the uuid form field
        this.customerCaptureForm.get("uuid").disable(true);

    }

    public ngDoCheck() {

        // Determine editor mode based on existence of a passed in customer
        this.editorMode = this.customer ? EditorMode.update : EditorMode.create;

        if (this.editorMode === EditorMode.update && !this._isInitialized) {
            this.customerCaptureForm.get("uuid").setValue(this.customer.uuid);
            this.customerCaptureForm.get("name").setValue(this.customer.name);
            this.customerCaptureForm.get("street").setValue(this.customer.address.street);
            this.customerCaptureForm.get("housenumber").setValue(this.customer.address.streetNo);
            this.customerCaptureForm.get("postalcode").setValue(this.customer.address.zipCode);
            this.customerCaptureForm.get("city").setValue(this.customer.address.city);
            this._isInitialized = true;
        }
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public cancel() {
        this.customerCaptureForm.reset();
        this.saveCustomerCancellationEvent.emit(null);
    }

    public createCustomer() {
        this.saveCustomerEvent.emit(
            new SaveCustomerEvent(
                this.customerCaptureForm.get("name").value,
                new Address(
                    this.customerCaptureForm.get("street").value,
                    this.customerCaptureForm.get("housenumber").value,
                    this.customerCaptureForm.get("postalcode").value,
                    this.customerCaptureForm.get("city").value
                ),
                this.customerCaptureForm.get("uuid").value
            )
        )
    }
}