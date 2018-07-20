import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import { Subscription } from "rxjs";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import { ErrorSlice } from "../store/error.slice";
import { InitializeErrorSliceAction } from "../store/error.actions";

/*
 * The ErrorComponent is responsible for displaying error messages propagated by other components.
 * It subscribes to the store and display as message as soon as the store contains one.
 */
@Component({
  selector: "educama-error",
  templateUrl: "./error.component.html"
})
export class ErrorComponent implements OnInit, OnDestroy {

  public messageKey: string;
  public messageText: string;
  public errorVisible = false;

  private subscription: Subscription;

  constructor(private _store: Store<State>) {
  }

  public ngOnInit() {
    this.subscription = this._store
                            .select(state => state.errorSlice)
                            .subscribe(errorSlice => this.updateModel(errorSlice));
  }

  public ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  public hideError() {
    this._store.dispatch(new InitializeErrorSliceAction);
  }

  private updateModel(errorSlice: ErrorSlice): void {
    this.messageKey = errorSlice.messageKey;
    this.messageText = errorSlice.messageText;
    this.errorVisible = errorSlice.visible;
  }
}
