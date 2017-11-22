import { Component, OnInit } from '@angular/core';
import { Cargo } from '../../models/cargo';
import { CargoFormService } from './cargo-form.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
    selector: 'cargo-form',
    templateUrl: './cargo-form.component.html',
})

export class CargoFormComponent implements OnInit {

    model: Cargo;
    submitted = false;

    constructor(private cargoFormService: CargoFormService) { }

    onSubmit() { this.submitted = true; }

    ngOnInit() {
        this.model = new Cargo();
    }

    sendEvent() {
        this.cargoFormService.sendEvent(this.model)
            .subscribe(res => {
                alert('Cargo event sent: \n' +
                    '\n Booking Id: ' + res.bookingId +
                    '\n Tracking number: ' + res.trackingNumber +
                    '\n Supplier ID: ' + res.supplierID +
                    '\n Origin: ' + res.origin +
                    '\n Destination: ' + res.destination);
            });
    }
}
