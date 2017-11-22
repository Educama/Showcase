import { Component, Input, OnInit } from '@angular/core';

import { Cargo } from '../../models/cargo';
import { CargoListService } from './cargo-list.service';

@Component({
  selector: 'cargo-list',
  templateUrl: './cargo-list.component.html',
  styleUrls: ['./cargo-list.component.css']
})
export class CargoListComponent implements OnInit {

  @Input() cargo: Cargo;
  isLoading: boolean = false;

  constructor(private cargoListService: CargoListService) { }

  ngOnInit() {
  }

  confirmHaulierBooking() {
    this.isLoading = true;
    this.cargoListService.confirmHaulierBooking(this.cargo.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      cargo => this.cargo = cargo
      )
  }

  declineHaulierBooking() {
    this.isLoading = true;
    this.cargoListService.declineHaulierBooking(this.cargo.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      cargo => this.cargo = cargo
      )
  }

  pickedUpCargo() {
    this.isLoading = true;
    this.cargoListService.pickedUpCargo(this.cargo.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      cargo => this.cargo = cargo
      )
  }

  deliveredCargo() {
    this.isLoading = true;
    this.cargoListService.deliveredCargo(this.cargo.bookingId)
      .finally(() => this.isLoading = false)
      .subscribe(
      cargo => this.cargo = cargo
      )
  }

}
