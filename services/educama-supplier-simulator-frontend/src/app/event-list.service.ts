import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Headers, RequestOptions} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Cargo }    from './cargo';
import { Flight } from './flight';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class EventListService {
    // Url to API
    private postUrl = 'https://educama-supplier-simulator-backend.mybluemix.net';
 
    // Injecting the http client into the service
    constructor(private http: Http) {}
 
    // Method retrieve all the flights
    getFlights (): Observable<Flight[]> {
        return this.http.get(this.postUrl+"/api/flights")
            .map(this.parseData)
            .catch(this.handleError);
    }

    // Method retrieve all the cargos
    getCargos (): Observable<Cargo[]> {
        return this.http.get(this.postUrl+"/api/cargos")
            .map(this.parseData)
            .catch(this.handleError);
    }
 
    // This method parses the data to JSON
    private parseData(res: Response)  {
        return res.json() || [];
    }
 
    // Displays the error message
    private handleError(error: Response | any) {
        let errorMessage: string;
 
        errorMessage = error.message ? error.message : error.toString();
 
        // This returns another Observable for the observer to subscribe to
        return Observable.throw(errorMessage);
    }
}