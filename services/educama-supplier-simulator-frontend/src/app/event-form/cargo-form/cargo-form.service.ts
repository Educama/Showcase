import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Cargo } from '../../models/cargo';

@Injectable()
export class CargoFormService {
  private url = 'https://educama-supplier-simulator-backend-sandbox.mybluemix.net/api/haulierBooking';

  constructor(private http: Http) { }

  sendEvent(event: Cargo): Observable<any> {
    return this.http.post(this.url, event).map(res => res.json());
  }

}
