import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Cargo } from '../../models/cargo';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class CargoListService {
    // Url to API
    private URL = 'https://educama-supplier-simulator-backend-sandbox.mybluemix.net/api/haulierBooking';

    // Injecting the http client into the service
    constructor(private http: Http) { }

    // Method retrieve all the cargos
    getCargos(): Observable<Cargo[]> {
        return this.http.get(this.URL)
            .map(this.parseData)
            .catch(this.handleError);
    }

    // Method confirm the haulier booking
    confirmHaulierBooking(bookingId: string): Observable<Cargo> {
        return this.http.post(`${this.URL}/${bookingId}/confirm`, {})
            .map((response: Response) => <Cargo>response.json())
            .catch(this.handleError);
    }

    // Method decline the haulier booking
    declineHaulierBooking(bookingId: string): Observable<Cargo> {
        return this.http.post(`${this.URL}/${bookingId}/reject`, {})
            .map((response: Response) => <Cargo>response.json())
            .catch(this.handleError);
    }

    // Method for picked up cargo
    pickedUpCargo(bookingId: string): Observable<Cargo> {
        return this.http.post(`${this.URL}/${bookingId}/pickedup`, {})
            .map((response: Response) => <Cargo>response.json())
            .catch(this.handleError);
    }

    // Method for picked up cargo
    deliveredCargo(bookingId: string): Observable<Cargo> {
        return this.http.post(`${this.URL}/${bookingId}/delivered`, {})
            .map((response: Response) => <Cargo>response.json())
            .catch(this.handleError);
    }

    // This method parses the data to JSON
    private parseData(res: Response) {
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