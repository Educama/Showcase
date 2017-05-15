import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";

/*
 * The ErrorService provides operations to show error messages.
 * It uses Observables that an error component can subscribe to to fetch and display error messages.
 * Other components or services can use this error service to propagate error messages not knowing where 
 * it is used and rendered.
 */
@Injectable()
export class ErrorService {

    // String sources
    private errorMessageSource = new Subject<string>();
    private errorHideSource = new Subject<boolean>();

    // Observable string sources
    public errorMessage$ = this.errorMessageSource.asObservable();
    public errorHide$ = this.errorHideSource.asObservable();

    /*
     * Main method to propagate an error message
     * @param error The error message to be displayed
     */
    public showError(error: string) {
        this.errorMessageSource.next(error);
    }
}