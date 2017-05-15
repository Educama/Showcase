import {Component} from "@angular/core";
import {ErrorService} from "../services/error.service";
import {Subscription} from "rxjs/Subscription";

/*
 * The ErrorComponent is responsible for displaying error messages propagated by other components or services via 
 * the ErrorService. It subscribes to the Observable used by the ErrorService.
 */
@Component({
    selector: "educama-error",
    templateUrl: "./error.component.html"
})
export class ErrorComponent {

    public errorMessage = "";
    public errorVisible = false;

    private subscription: Subscription;

    constructor(private _errorService: ErrorService) {
        this.subscription = _errorService.errorMessage$.subscribe(
            error => {
                this.errorMessage = error;
                this.errorVisible = true;
            }
        );
    }

    private hideError() {
        this.errorVisible = false;
    }
}