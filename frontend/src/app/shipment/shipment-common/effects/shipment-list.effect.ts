import { of as observableOf } from "rxjs";

import {
  catchError,
  map,
  mergeMap,
  switchMap,
  withLatestFrom
} from "rxjs/operators";
import { Injectable } from "@angular/core";
import {
  Actions,
  Effect
} from "@ngrx/effects";
import { ShipmentService } from "../api/shipment.service";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import * as actions from "../store/shipments/shipment-list-page/shipment-list-page.actions";
import {
  RequestShipmentsFailedAction,
  RequestShipmentsSuccessfulAction,
  RequestSingleShipment
} from "../store/shipments/shipment-list-page/shipment-list-page.actions";
import * as organizeFlightactions from "../store/shipments/organize-flight-page/organize-flight-page.actions";
import { SaveFlightSuccessfultAction } from "../store/shipments/organize-flight-page/organize-flight-page.actions";
import { LoadShipmentSuccessfullAction } from "../store/shipments/shipment-capture-page/shipment-capture-page.actions";
import { RequestTasksForShipmentAction } from "../store/tasks/task-list-page.actions";
import { RequestCompletedTaskForShipmentAction } from "../store/completed-tasks/completed-task-list-page.actions";
import { ReloadShipmentAndTasksForCaseUiACtion } from "../store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";
import { RequestEnabledTasksForShipmentAction } from "../store/enbaled-tasks/enabled-task-list-page.actions";

@Injectable()
export class ShipmentListEffect {

  constructor(private _actions: Actions,
              private _shipmentService: ShipmentService,
              private _store: Store<State>) {
  }

  @Effect() loadShipments = this._actions
                                .ofType(actions.REQUEST_SHIPMENTS)
                                .pipe(
                                  withLatestFrom(this._store, (action, state) => state.shipmentListSlice),
                                  switchMap(() => {
                                    return this._shipmentService.findShipments();
                                  }),
                                  map(shipmentListResource => new RequestShipmentsSuccessfulAction(shipmentListResource)),
                                  catchError(() => observableOf(new RequestShipmentsFailedAction()))
                                );

  @Effect() loadUniqueShipment = this._actions
                                     .ofType(actions.REQUEST_SINGLE_SHIPMENT)
                                     .pipe(
                                       map((action: actions.RequestSingleShipment) => action.payload),
                                       switchMap((trackingId: string) => {
                                         return this._shipmentService.findShipmentbyId(trackingId);
                                       }),
                                       map(shipmentListResource => new LoadShipmentSuccessfullAction(shipmentListResource))
                                     );

  @Effect() addFLightToShipment = this._actions
                                      .ofType(organizeFlightactions.SAVE_FLIGHT_ACTION)
                                      .pipe(
                                        switchMap((action: organizeFlightactions.SaveFlightAction) => {
                                          return this._shipmentService.addFlightToShipment(action.trackingId, action.payload);
                                        }),
                                        mergeMap(taskListSlice => [
                                          new SaveFlightSuccessfultAction(taskListSlice.shipmentFlight, taskListSlice.trackingId),
                                          new ReloadShipmentAndTasksForCaseUiACtion(taskListSlice.trackingId),
                                          new RequestCompletedTaskForShipmentAction(taskListSlice.trackingId),
                                          new RequestSingleShipment(taskListSlice.trackingId),
                                          new RequestTasksForShipmentAction(taskListSlice.trackingId),
                                          new RequestEnabledTasksForShipmentAction(taskListSlice.trackingId)
                                        ])
                                      );
}
