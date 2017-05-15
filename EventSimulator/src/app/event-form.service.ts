import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { EventForm }    from './event-form';

@Injectable()
export class EventFormService {
  private url = 'https://eventbackend.mybluemix.net/api/events';

  constructor(private http: Http) { }

  sendEvent(event: EventForm) : Observable<any> {
    return this.http.post(this.url, event).map(res => res.json());
  }

}