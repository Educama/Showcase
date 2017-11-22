import { Component, OnInit } from '@angular/core';
import { Flight } from '../../models/flight';
import { FlightFormService } from './flight-form.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
    selector: 'flight-form',
    templateUrl: './flight-form.component.html',
})

export class FlightFormComponent implements OnInit {

    model: Flight;
    submitted = false;

    constructor(private flightFormService: FlightFormService) { }

    onSubmit() { this.submitted = true; }

    ngOnInit() {
        this.model = new Flight();
    }

    sendEvent() {
        this.flightFormService.sendEvent(this.model)
            .subscribe(res => {
                alert('Flight event sent: \n' +
                    '\n Booking Id: ' + res.bookingId +
                    '\n Tracking number: ' + res.trackingNumber +
                    '\n Airline: ' + res.airline +
                    '\n Flight number: ' + res.flightNumber +
                    '\n Origin: ' + res.origin +
                    '\n Destination: ' + res.destination);
            });
    }
}