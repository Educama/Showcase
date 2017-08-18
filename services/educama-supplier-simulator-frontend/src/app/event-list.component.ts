import { Component, OnInit } from '@angular/core';
import { Flight } from './flight';
import { Cargo } from './cargo';
import { EventListService } from './event-list.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
  selector: 'event-list',
  templateUrl: './event-list.component.html',
  providers: [EventListService]
})

export class EventListComponent implements OnInit  {

    flights: Flight[];
    cargos: Cargo[];
    submitted = false;
    errorMessage : string

    constructor(private eventListService: EventListService) {}

    ngOnInit() {
        this.getFlights();
        this.getCargos();
    }

    getFlights() {
        this.eventListService.getFlights()
            .subscribe(
                flights => this.flights = flights,
                error => this.errorMessage = error
            )
    }

   getCargos() {
        this.eventListService.getCargos()
            .subscribe(
                cargos => this.cargos = cargos,
                error => this.errorMessage = error
            )
    }

    onSubmit() { this.submitted = true; }
    
}