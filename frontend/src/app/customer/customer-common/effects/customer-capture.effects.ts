import { of as observableOf } from "rxjs";
import {
  catchError,
  map,
  switchMap,
  tap
} from "rxjs/operators";
import { Injectable } from "@angular/core";
import {
  Actions,
  Effect
} from "@ngrx/effects";
import * as actions from "../store/customer-capture-page.actions";
import {
  PostCustomerAction,
  PostCustomerFailedAction,
  PostCustomerSuccessfulAction,
  PutCustomerAction,
  PutCustomerFailedAction,
  PutCustomerSuccessfulAction,
  SaveCustomerAction
} from "../store/customer-capture-page.actions";
import { CustomerService } from "../api/customer.service";
import { CustomerResource } from "../api/resources/customer.resource";
import { Router } from "@angular/router";
import * as _ from "lodash";

@Injectable()
export class CustomerCaptureEffect {

  constructor(private _actions: Actions,
              private _customerService: CustomerService,
              private _router: Router) {
  }

  @Effect() saveCustomer = this._actions
                               .ofType(actions.SAVE_CUSTOMER)
                               .pipe(
                                 map((action: SaveCustomerAction) => _.isEmpty(action.payload.uuid)
                                   ? new PostCustomerAction(action.payload) : new PutCustomerAction(action.payload))
                               );

  @Effect() postCustomer = this._actions
                               .ofType(actions.POST_CUSTOMER)
                               .pipe(
                                 switchMap((action: PostCustomerAction) => this._customerService.createCustomer(action.payload)),
                                 map((customerResource: CustomerResource) => new PostCustomerSuccessfulAction(customerResource)),
                                 catchError(() => observableOf(new PostCustomerFailedAction()))
                               );

  @Effect() putCustomer = this._actions
                              .ofType(actions.PUT_CUSTOMER)
                              .pipe(
                                switchMap((action: PutCustomerAction) => this._customerService.updateCustomer(action.payload.uuid, action.payload)),
                                map((customerResource: CustomerResource) => new PutCustomerSuccessfulAction(customerResource)),
                                catchError(() => observableOf(new PutCustomerFailedAction()))
                              );

  // Should this be an "effect"?
  @Effect({ dispatch: false }) routeToCustomerList = this._actions
                                                         .ofType(...[
                                                           actions.POST_CUSTOMER_SUCCESSFUL,
                                                           actions.PUT_CUSTOMER_SUCCESSFUL
                                                         ])
                                                         .pipe(tap(() => this._router.navigate([ "/customers" ])));
}
