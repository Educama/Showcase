import { Component, OnInit } from '@angular/core';
import { FlightForm }    from './flight-form';
import { FlightFormService } from './flight-form.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
  selector: 'flight-form',
  templateUrl: './flight-form.component.html',
})

export class FlightFormComponent implements OnInit  {

    model: FlightForm;
    submitted = false;

    constructor(private flightFormService: FlightFormService) {}

    onSubmit() { this.submitted = true; }

    ngOnInit() {
        this.model = new FlightForm();
    }

    sendEvent() {
        
        alert('Flight event sent: \n' +
              '\n Tracking number: '+ this.model.trackingNumber +
              '\n Airline: ' + this.model.airline +
              '\n Flight number: ' + this.model.flightNumber +
              '\n Origin: ' + this.model.origin +
              '\n Destination: ' + this.model.destination);

        this.flightFormService.sendEvent(this.model)
            .subscribe(res => {

            });         
    }
}