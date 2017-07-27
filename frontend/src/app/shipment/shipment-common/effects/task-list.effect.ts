import {Injectable} from "@angular/core";
import {Actions, Effect} from "@ngrx/effects";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import * as actions from "../store/shipments/shipment-list-page.actions";
import {ShipmentListSlice} from "../store/shipments/shipment-list-page.slice";
import {Observable} from "rxjs/Observable";
import {TaskService} from "../api/task.service";
import {RequestTasksFailedAction, RequestTasksSuccessfulAction} from "../store/tasks/task-list-page.actions";

@Injectable()
export class TaskListEffect {

    constructor(private _actions: Actions,
                private _taskService: TaskService,
                private _store: Store<State>) {
    }

    @Effect() loadTasks = this._actions
        .ofType(actions.REQUEST_SHIPMENTS)
        .withLatestFrom(this._store, (action, state) => state.shipmentListSlice)
        .switchMap((taskListSlice: ShipmentListSlice) => {
            return this._taskService.findTasks();
        })
        .map(taskListSlice => new RequestTasksSuccessfulAction(taskListSlice))
        .catch(() => Observable.of(new RequestTasksFailedAction()));

}
