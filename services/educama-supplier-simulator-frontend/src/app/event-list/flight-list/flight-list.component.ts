import { Component, Input, OnInit } from '@angular/core';
import 'rxjs/add/operator/finally';

import { Flight } from '../../models/flight';
import { FlightListService } from './flight-list.service';

@Component({
  selector: 'flight-list',
  templateUrl: './flight-list.component.html',
  styleUrls: ['./flight-list.component.css']
})

export class FlightListComponent implements OnInit {

  @Input() flight: Flight;
  isLoading: boolean = false;

  constructor(private flightListService: FlightListService) { }

  ngOnInit() {
  }

  confirmAirlineBooking() {
    this.isLoading = true;
    this.flightListService.confirmAirlineBooking(this.flight.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      flight => this.flight = flight
      )
  }

  declineAirlineBooking() {
    this.isLoading = true;
    this.flightListService.declineAirlineBooking(this.flight.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      flight => this.flight = flight
      )
  }

  departedFlight() {
    this.isLoading = true;
    this.flightListService.departedFlight(this.flight.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      flight => this.flight = flight
      )
  }

  arrivedFlight() {
    this.isLoading = true;
    this.flightListService.arrivedFlight(this.flight.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      flight => this.flight = flight
      )
  }
}
