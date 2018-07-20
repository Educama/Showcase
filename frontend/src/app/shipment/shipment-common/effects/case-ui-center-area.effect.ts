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
import * as actions from "../store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";
import {
  LoadActiveTasksForCaseUiCenterAreaAction,
  LoadActiveTasksSuccesfullForCaseUiCenterAreaAction,
  LoadCompletedTasksForCaseUiCenterAreaAction,
  LoadCompletedTasksSuccessfulForCaseUiCenterAreaAction,
  LoadEnabledTasksForCaseUiCenterAreaAction,
  LoadEnabledTasksSuccessfulForCaseUiCenterAreaAction,
  LoadInvoiceSuccessfulForCaseUiCenterAreaAction,
  LoadShipmentForCaseUiCenterAreaAction,
  LoadShipmentSuccesfullForCaseUiCenterAreaAction
} from "../store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";
import { ShipmentService } from "../api/shipment.service";
import { TaskService } from "../api/task.service";

@Injectable()
export class CaseUiCenterAreaEffect {
  constructor(private _actions: Actions,
              private _shipmentService: ShipmentService,
              private _taskService: TaskService) {
  }

  @Effect()
  loadShipment = this._actions
                     .ofType(actions.LOAD_SHIPMENT_FOR_CENTER_AREA_ACTION)
                     .pipe(
                       switchMap((action: actions.LoadShipmentForCaseUiCenterAreaAction) => {
                           return this._shipmentService.findShipmentbyId(action.trackingID);
                         }
                       ),
                       map(shipment => new LoadShipmentSuccesfullForCaseUiCenterAreaAction(shipment))
                     );

  @Effect()
  loadActiveTask = this._actions
                       .ofType(actions.LOAD_ACTIVE_TASKS_FOR_CENTER_AREA_ACTION)
                       .pipe(
                         switchMap((action: actions.LoadShipmentForCaseUiCenterAreaAction) => {
                             return this._taskService.findTasksForShipment(action.trackingID);
                           }
                         ),
                         map(activeTaskList => new LoadActiveTasksSuccesfullForCaseUiCenterAreaAction(activeTaskList))
                       );

  @Effect()
  loadEnabledTask = this._actions
                        .ofType(actions.LOAD_ENABLED_TASKS_FOR_CENTER_AREA_ACTION)
                        .pipe(
                          switchMap((action: actions.LoadEnabledTasksForCaseUiCenterAreaAction) => {
                              return this._taskService.findEnabledTasksToShipment(action.trackingID);
                            }
                          ),
                          map(enabledTaskList => new LoadEnabledTasksSuccessfulForCaseUiCenterAreaAction(enabledTaskList))
                        );

  @Effect()
  loadCompletedTask = this._actions
                          .ofType(actions.LOAD_COMPLETED_TASKS_FOR_CENTER_AREA_ACTION)
                          .pipe(
                            switchMap((action: actions.LoadCompletedTasksForCaseUiCenterAreaAction) => {
                                return this._taskService.findCompletedTasksForShipment(action.trackingID);
                              }
                            ),
                            map(completedTaskList => new LoadCompletedTasksSuccessfulForCaseUiCenterAreaAction(completedTaskList))
                          );

  @Effect()
  loadInvoice = this._actions
                    .ofType(actions.LOAD_INVOICE_FOR_CENTER_AREA_ACTION)
                    .pipe(
                      switchMap((action: actions.LoadInvoiceForCaseUiCenterAreaAction) => {
                          return this._shipmentService.getInvoice(action.trackingID);
                        }
                      ),
                      map(invoice => new LoadInvoiceSuccessfulForCaseUiCenterAreaAction(invoice))
                    );

  @Effect()
  reloadShipmentAndTaskShipment = this._actions
                                      .ofType(actions.RELOAD_SHIPMENT_AND_TASK_FOR_CASE_UI_ACTION)
                                      .pipe(
                                        mergeMap((reload: actions.ReloadShipmentAndTasksForCaseUiACtion) => [
                                          new LoadShipmentForCaseUiCenterAreaAction(reload.trackingId),
                                          new LoadActiveTasksForCaseUiCenterAreaAction(reload.trackingId),
                                          new LoadEnabledTasksForCaseUiCenterAreaAction(reload.trackingId),
                                          new LoadCompletedTasksForCaseUiCenterAreaAction(reload.trackingId)
                                        ])
                                      );
}
