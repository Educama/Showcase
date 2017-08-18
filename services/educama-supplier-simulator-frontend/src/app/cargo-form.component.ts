import { Component, OnInit } from '@angular/core';
import { Cargo }    from './cargo';
import { CargoFormService } from './cargo-form.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
  selector: 'cargo',
  templateUrl: './cargo-form.component.html',
})

export class CargoFormComponent implements OnInit  {

    model: Cargo;
    submitted = false;

    constructor(private cargoFormService: CargoFormService) {}

    onSubmit() { this.submitted = true; }

    ngOnInit() {
        this.model = new Cargo();
    }

    sendEvent() {
        
        alert('Cargo event sent: \n' +
              '\n Tracking number: '+ this.model.trackingNumber +
              '\n Supplier ID: ' + this.model.supplierID +
              '\n Origin: ' + this.model.origin +
              '\n Destination: ' + this.model.destination);

        this.cargoFormService.sendEvent(this.model)
            .subscribe(res => {

            });         
    }
}