import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Flight } from '../../models/flight';

@Injectable()
export class FlightFormService {
  private url = 'https://educama-supplier-simulator-backend-sandbox.mybluemix.net/api/airlineBooking';

  constructor(private http: Http) { }

  sendEvent(event: Flight): Observable<any> {
    return this.http.post(this.url, event).map(res => res.json());
  }

}
