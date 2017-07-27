import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {ShipmentService} from "../api/shipment.service";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import * as actions from "../store/shipments/shipment-list-page.actions";
import {ShipmentListSlice} from "../store/shipments/shipment-list-page.slice";
import {RequestShipmentsFailedAction, RequestShipmentsSuccessfulAction} from "../store/shipments/shipment-list-page.actions";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ShipmentListEffect {

    constructor(private _actions: Actions,
                private _shipmentService: ShipmentService,
                private _store: Store<State>) {
    }

    @Effect() loadShipments = this._actions
        .ofType(actions.REQUEST_SHIPMENTS)
        .withLatestFrom(this._store, (action, state) => state.shipmentListSlice)
        .switchMap((shipmentListSlice: ShipmentListSlice) => {
            return this._shipmentService.findShipments();
        })
        .map(shipmentListResource => new RequestShipmentsSuccessfulAction(shipmentListResource))
        .catch(() => Observable.of(new RequestShipmentsFailedAction()));

}
