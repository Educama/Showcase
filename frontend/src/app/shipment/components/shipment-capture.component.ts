import {Component, Output, OnInit, EventEmitter} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";

@Component({
    selector: "educama-shipment-capture",
    templateUrl: "./shipment-capture.component.html"
})
export class ShipmentCaptureComponent implements OnInit {

    @Output()
    public createShipmentEvent: EventEmitter<CreateShipmentEvent> = new EventEmitter<CreateShipmentEvent>();

    @Output()
    public createShipmentCancellationEvent = new EventEmitter();

    public shipmentCaptureForm: FormGroup;

    constructor(private _formBuilder: FormBuilder) {
    }

    public ngOnInit() {
        this.shipmentCaptureForm = this._formBuilder.group({
            customer: ["", [Validators.required]],
            senderAddress: ["", [Validators.required, Validators.maxLength(100)]],
            receiverAddress: ["", [Validators.required, Validators.maxLength(100)]],
        });
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public cancel() {
        this.shipmentCaptureForm.reset();
        this.createShipmentCancellationEvent.emit(null);
    }

    public createShipment() {
        this.createShipmentEvent.emit(
            new CreateShipmentEvent(
                this.shipmentCaptureForm.get("customer").value,
                this.shipmentCaptureForm.get("senderAddress").value,
                this.shipmentCaptureForm.get("receiverAddress").value
            )
        )
    }
}

export class CreateShipmentEvent {
    customer: string;
    senderAddress: string;
    receiverAddress: string

    constructor(customer: string, senderAddress: string, receiverAddress: string) {
        this.customer = customer;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }
}