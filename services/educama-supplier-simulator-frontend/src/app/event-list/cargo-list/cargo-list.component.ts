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

  constructor(private cargoListService: CargoListService) { }

  ngOnInit() {
  }

  refresh(): void {
    window.location.reload();
  }

  confirmHaulierBooking() {
    this.cargoListService.confirmHaulierBooking(this.cargo._id)
    .subscribe(
        cargos => {}
    )
  }

  declineHaulierBooking() {
    this.cargoListService.declineHaulierBooking(this.cargo._id)
    .subscribe(
        cargos => {}
    )
  }

  pickedUpCargo() {
    this.cargoListService.pickedUpCargo(this.cargo._id)
    .subscribe(
        cargos => {}
    )
  }

  deliveredCargo() {
    this.cargoListService.deliveredCargo(this.cargo._id)
    .subscribe(
        cargos => {}
    )
  }

}
