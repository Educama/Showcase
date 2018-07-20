import {
  map,
  mergeMap,
  switchMap
} from "rxjs/operators";
import { Injectable } from "@angular/core";
import {
  Actions,
  Effect
} from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import * as actions from "../store/shipments/invoice-page/invoice-page.actions";
import {
  CreateInvoiceSuccessfulAction,
  GetInvoiceForShipmentSucessfullAction
} from "../store/shipments/invoice-page/invoice-page.actions";
import { TaskService } from "../api/task.service";
import { ShipmentService } from "../api/shipment.service";
import { RequestTasksForShipmentAction } from "../store/tasks/task-list-page.actions";
import { RequestCompletedTaskForShipmentAction } from "../store/completed-tasks/completed-task-list-page.actions";
import { ReloadShipmentAndTasksForCaseUiACtion } from "../store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";

@Injectable()
export class InvoiceEffects {
  constructor(private _actions: Actions,
              private _taskService: TaskService,
              private _store: Store<State>,
              private _shipmentService: ShipmentService) {
  }

  private lastId: string;
  @Effect()
  createInvoice = this._actions
                      .ofType(actions.CREATE_INVOICE_ACTION)
                      .pipe(
                        switchMap((action: actions.CreateInvoiceAction) => {
                          this.lastId = action.trackingID;
                          return this._shipmentService.createInvoice(action.trackingID, action.payload);
                        }),
                        mergeMap(invoice => [
                          new CreateInvoiceSuccessfulAction(invoice),
                          new RequestTasksForShipmentAction(this.lastId),
                          new RequestCompletedTaskForShipmentAction(this.lastId),
                          new ReloadShipmentAndTasksForCaseUiACtion(this.lastId)
                        ])
                      );

  @Effect()
  getInvoice = this._actions
                   .ofType(actions.GET_INVOICE_FOR_SHIPMENT_ACTION)
                   .pipe(
                     switchMap((action: actions.GetInvoiceForShipmentAction) => {
                       return this._shipmentService.getInvoice(action.trackingId);
                     }),
                     map(invoice =>
                       new GetInvoiceForShipmentSucessfullAction(invoice)
                     )
                   );
}

