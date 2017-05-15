import { Component, OnInit } from '@angular/core';
import { EventForm }    from './event-form';
import { EventFormService } from './event-form.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Component({
  selector: 'event-form',
  templateUrl: './event-form.component.html',
})

export class EventFormComponent implements OnInit  {

    model: EventForm;
    submitted = false;

    constructor(private eventFormService: EventFormService) {}

    onSubmit() { this.submitted = true; }

    ngOnInit() {
        this.model = new EventForm();
    }

    sendEvent() {
        
        alert('Event sent');

        this.eventFormService.sendEvent(this.model)
            .subscribe(res => {

            });         
    }
}