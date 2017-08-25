import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Headers, RequestOptions} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Cargo }    from '../../models/cargo';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class CargoListService {
    // Url to API
    private URL = 'https://educama-supplier-simulator-backend.mybluemix.net/api/cargos';

    // Injecting the http client into the service
    constructor(private http: Http) {}
 
    // Method retrieve all the cargos
    getCargos (): Observable<Cargo[]> {
        return this.http.get(this.URL)
            .map(this.parseData)
            .catch(this.handleError);
    }

    // Method confirm the haulier booking
    confirmHaulierBooking(haulierBooking: string): Observable<Cargo[]> {
        return this.http.put(`${this.URL}/${haulierBooking}`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method decline the haulier booking
    declineHaulierBooking(haulierBooking: string): Observable<Cargo[]> {
        return this.http.delete(`${this.URL}/${haulierBooking}`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method for picked up cargo
    pickedUpCargo(haulierBooking: string): Observable<Cargo[]> {
        return this.http.put(`${this.URL}/${haulierBooking}/pickedup`, {})
        .map(this.parseData)
        .catch(this.handleError);
    }

    // Method for picked up cargo
    deliveredCargo(haulierBooking: string): Observable<Cargo[]> {
        return this.http.put(`${this.URL}/${haulierBooking}/delivered`, {})
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