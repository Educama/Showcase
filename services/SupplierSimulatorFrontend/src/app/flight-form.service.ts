import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { FlightForm }    from './flight-form';

@Injectable()
export class FlightFormService {
  private url = 'https://suppliersimulatorbackend.mybluemix.net/api/flights';

  constructor(private http: Http) { }

  sendEvent(event: FlightForm) : Observable<any> {
    return this.http.post(this.url, event).map(res => res.json());
  }

}