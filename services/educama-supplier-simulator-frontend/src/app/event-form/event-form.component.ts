import { Component, OnInit } from '@angular/core';
import { EventListComponent } from '../event-list/event-list.component'
import { FlightListService } from '../event-list/flight-list/flight-list.service'
import { CargoListService } from '../event-list/cargo-list/cargo-list.service'

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.css'],
  providers: [EventListComponent, FlightListService, CargoListService],
})
export class EventFormComponent implements OnInit {

  constructor(private eventListComponent: EventListComponent) { }

  ngOnInit() {
  }

  showFlights() {
    this.eventListComponent.showFlights;
  }

  showCargos() {
    this.eventListComponent.showCargos;
  }

  showAll() {
    this.eventListComponent.showAll;
  }

}
