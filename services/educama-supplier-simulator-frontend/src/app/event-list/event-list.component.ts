import { Component, OnInit } from '@angular/core';
import { Flight } from '../models/flight';
import { Cargo } from '../models/cargo';
import { FlightListService } from './flight-list/flight-list.service';
import { CargoListService } from './cargo-list/cargo-list.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
    selector: 'event-list',
    templateUrl: './event-list.component.html',
    styleUrls: ['./event-list.component.css'],
    providers: [FlightListService, CargoListService],
})

export class EventListComponent implements OnInit {

    flights: Flight[];
    cargos: Cargo[];
    errorMessage: string;
    displayed: boolean = false;
    displayFlights: boolean = true;
    displayCargos: boolean = true;

    constructor(private flightListService: FlightListService, private cargoListService: CargoListService) { }

    ngOnInit() {
        this.getFlights();
        this.getCargos();
    }

    getFlights() {
        this.displayed = true;
        this.flightListService.getFlights()
            .subscribe(
            flights => {
                this.flights = flights;
                this.displayed = false;
            },
            error => {
                this.errorMessage = error;
                this.displayed = false;
            }
            )
    }

    getCargos() {
        this.cargoListService.getCargos()
            .subscribe(
            cargos => {
                this.cargos = cargos;
                this.displayed = false;
            },
            error => {
                this.errorMessage = error;
                this.displayed = false;
            }
            )
    }

    confirmAirlineBooking(airlineBooking: string) {
        this.flightListService.confirmAirlineBooking(airlineBooking)
            .subscribe(
            flights => { },
            error => { this.errorMessage = error }
            )
    }

    showFlights() {
        this.displayFlights = true;
        this.displayCargos = false;
    }

    showCargos() {
        this.displayCargos = true;
        this.displayFlights = false;
    }

    showAll() {
        this.displayFlights = true;
        this.displayCargos = true;
    }

}
