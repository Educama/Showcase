import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Headers, RequestOptions} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Flight } from '../../models/flight';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class FlightListService {
    // Url to API
    private URL = 'https://educama-supplier-simulator-backend.mybluemix.net/api/flights';

    // Injecting the http client into the service
    constructor(private http: Http) {}
 
    // Method retrieve all the flights
    getFlights (): Observable<Flight[]> {
        return this.http.get(this.URL)
            .map(this.parseData)
            .catch(this.handleError);
    }

    // Method confirm the airline booking
    confirmAirlineBooking(airlineBooking: string): Observable<Flight[]> {
        return this.http.put(`${this.URL}/${airlineBooking}`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method decline the airline booking
    declineAirlineBooking(airlineBooking: string): Observable<Flight[]> {
        return this.http.delete(`${this.URL}/${airlineBooking}`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method for departed flight
    departedFlight(airlineBooking: string): Observable<Flight[]> {
        return this.http.put(`${this.URL}/${airlineBooking}/departed`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method for arrived flight
    arrivedFlight(airlineBooking: string): Observable<Flight[]> {
        return this.http.put(`${this.URL}/${airlineBooking}/arrived`, {})
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