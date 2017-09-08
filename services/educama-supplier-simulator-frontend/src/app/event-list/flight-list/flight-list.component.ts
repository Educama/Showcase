import { Component, Input, OnInit } from '@angular/core';

import { Flight } from '../../models/flight';
import { FlightListService } from './flight-list.service';

@Component({
  selector: 'flight-list',
  templateUrl: './flight-list.component.html',
  styleUrls: ['./flight-list.component.css']
})

export class FlightListComponent implements OnInit {

  @Input() flight: Flight;

  constructor(private flightListService: FlightListService) { }

  ngOnInit() {
  }

  confirmAirlineBooking() {
    this.flightListService.confirmAirlineBooking(this.flight._id)
    .subscribe(
        flights => {}
    )
  }

  declineAirlineBooking() {
    this.flightListService.declineAirlineBooking(this.flight._id)
    .subscribe(
        flights => {}
    )
  }

  departedFlight() {
    this.flightListService.departedFlight(this.flight._id)
    .subscribe(
        flights => {}
    )
  }

  arrivedFlight() {
    this.flightListService.arrivedFlight(this.flight._id)
    .subscribe(
        flights => {}
    )
  }
}
