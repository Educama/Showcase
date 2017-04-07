import {Component, Output, OnInit, EventEmitter} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {CustomerService} from "../../customer/api/customer.service";
import {CustomerResource} from "../../customer/api/resources/customer.resource";
import {Router} from "@angular/router";
import {ShipmentResource} from "../api/resources/shipment.resource";
import {ShipmentService} from "../api/shipment.service";

@Component({
    selector: "educama-shipment-capture",
    templateUrl: "./shipment-capture.component.html"
})
export class ShipmentCaptureComponent implements OnInit {

    @Output()
    public createShipmentCancellationEvent = new EventEmitter();

    public shipmentCaptureForm: FormGroup;
    public customerSuggestions: CustomerResource[];

    public senderStreet: string;
    public senderStreetNo: string;
    public senderZipCode: string;
    public senderCity: string;
    public senderUUID: string;

    public receiverStreet: string;
    public receiverStreetNo: string;
    public receiverZipCode: string;
    public receiverCity: string;
    public receiverUUID: string;

    public dangerousCheck: boolean = false;
    public exportInsuranceCheck: boolean = false;
    public exportClearanceCheck: boolean = false;
    public preCarriageCheck: boolean = false;
    public flightCheck: boolean = true;
    public onCarriageCheck: boolean = false;
    public importInsuranceCheck: boolean = false;
    public importClearanceCheck: boolean = false;
    public senderIsCustomerCheck: boolean = false;
    public receiverIsCustomerCheck: boolean = false;
    public isCustomer: string = "SENDER";

    constructor(private _formBuilder: FormBuilder,
                private _customerService: CustomerService,
                private _router: Router,
                private _shipmentService: ShipmentService) {
    }

    public ngOnInit() {
        this.shipmentCaptureForm = this._formBuilder.group({
            sender: ["", [Validators.required]],
            receiver: ["", [Validators.required]],
            numberPackages: ["", [Validators.pattern("[0-9]+")]],
            totalWeight: ["", [Validators.pattern("[0-9]+")]],
            totalCapacity: ["", [Validators.pattern("[0-9]+")]],
            cargoDescription: [""],
            dangerousGoods: [""],
            preCarriage: [""],
            exportInsurance: [""],
            exportCustomsClearance: [""],
            flight: [""],
            importInsurance: [""],
            importCustomsClearance: [""],
            onCarriage: [""],
            isCustomer: [""],
        });
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public loadUserSuggestions(event:any) {
        this._customerService.findCustomerSuggestions(event.query)
            .subscribe(customerSuggestionResource => this.customerSuggestions = customerSuggestionResource.customers);
    }

    public createNewCustomer() {
        this._router.navigate(["/customers/capture"]);
    }

    public onReceiverSelected(receiver: CustomerResource) {

        this.receiverStreet = receiver.address.street;
        this.receiverStreetNo = receiver.address.streetNo;
        this.receiverZipCode = receiver.address.zipCode;
        this.receiverCity = receiver.address.city;
        this.receiverUUID = receiver.uuid;
    }

    public onSenderSelected(sender: CustomerResource) {

        this.senderStreet = sender.address.street;
        this.senderStreetNo = sender.address.streetNo;
        this.senderZipCode = sender.address.zipCode;
        this.senderCity = sender.address.city;
        this.senderUUID = sender.uuid
    }

    public cancel() {
        this.shipmentCaptureForm.reset();
        this.createShipmentCancellationEvent.emit(null);
    }

    public createShipment() {

        let shipment = new ShipmentResource();

        shipment.onCarriage = this.shipmentCaptureForm.get("onCarriage").value;
        shipment.cargoDescription = this.shipmentCaptureForm.get("cargoDescription").value;
        shipment.isCustomer = this.shipmentCaptureForm.get("isCustomer").value;
        shipment.uuidSender = this.senderUUID;
        shipment.uuidReceiver = this.receiverUUID;
        shipment.totalWeight= this.shipmentCaptureForm.get("totalWeight").value;
        shipment.totalCapacity= this.shipmentCaptureForm.get("totalCapacity").value;
        shipment.dangerousGoods= this.shipmentCaptureForm.get("dangerousGoods").value;
        shipment.preCarriage= this.shipmentCaptureForm.get("preCarriage").value;
        shipment.exportInsurance= this.shipmentCaptureForm.get("exportInsurance").value;
        shipment.exportCustomsClearance= this.shipmentCaptureForm.get("exportCustomsClearance").value;
        shipment.flight= this.shipmentCaptureForm.get("flight").value;
        shipment.importInsurance= this.shipmentCaptureForm.get("importInsurance").value;
        shipment.importCustomsClearance= this.shipmentCaptureForm.get("importCustomsClearance").value;
        shipment.numberPackages= this.shipmentCaptureForm.get("numberPackages").value;

        this._shipmentService.createShipment(shipment).subscribe(shipment => {
            this._router.navigate(["/shipments"]);
        });

    }
}