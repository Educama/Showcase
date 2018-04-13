import {Component, OnDestroy, OnInit} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import {TaskListModel, TaskListRowModel} from "./task-list-page.model";
import {State} from "../../../app.reducers";
import {Address} from "../../../customer/customer-common/api/datastructures/address.datastructure";
import {ActivatedRoute, Router} from "@angular/router";
import {TaskListSlice} from "../../shipment-common/store/tasks/task-list-page.slice";
import {
  InitializeTaskListAction,
  RequestTasksAction, RequestTasksForShipmentAction
} from "../../shipment-common/store/tasks/task-list-page.actions";
import {TaskResource} from "../../shipment-common/api/resources/task.resource";
import {
  LoadSingleShipmentAction,
  RequestSingleShipment
} from "../../shipment-common/store/shipments/shipment-list-page/shipment-list-page.actions";

@Component({
  selector: "educama-task-list-page",
  templateUrl: `./task-list-page.component.html`
})
export class TaskListPageComponent implements OnInit, OnDestroy {

  // relevant slice of store and subscription for this slice
  public taskListSlice: Observable<TaskListSlice>;
  public taskListSliceSubscription: Subscription;
  public isCaseUi: boolean;

  // model for the page
  public taskListModel: TaskListModel = new TaskListModel();

  constructor(private _router: Router,
              private _store: Store<State>,
              private  _activatedRoute: ActivatedRoute) {

    this.taskListSlice = this._store.select(state => state.taskListSlice);
    this.taskListSliceSubscription = this.taskListSlice
      .subscribe(taskListSlice => this.updateTaskListModel(taskListSlice));
  }

  public ngOnInit() {
    if (this._router.url.includes("caseui")) {
      this._activatedRoute.params.subscribe(params => {
          this._store.dispatch(new RequestTasksForShipmentAction(params["id"]));
        }
      );
      this.isCaseUi = true;
    } else {
      this._store.dispatch(new RequestTasksAction());
      this.isCaseUi = false;
    }
  }

  public ngOnDestroy() {
    this._store.dispatch(new InitializeTaskListAction());
    this.taskListSliceSubscription.unsubscribe();
  }

  // ***************************************************
  // Event Handler
  // ***************************************************

  public onTaskSelectedEvent(task: TaskResource) {
    switch (task.name) {
      case "Change shipment order":
        this._router.navigate(["caseui/" + task.trackingId + "/changeShipment"]);
        break;
      case "Complete shipment order":
        this._router.navigate(["caseui/" + task.trackingId + "/completeShipment"]);
        break;
      case "Organize Flight":
        this._store.dispatch(new RequestSingleShipment(task.trackingId));
        this._router.navigate(["caseui/" + task.trackingId + "/organizeFlight"]);
        break;
      case "Create invoice":
        this._router.navigate(["caseui/" + task.trackingId + "/createInvoice"]);
        break;
        default:
        break;
    }
  }

  // ***************************************************
  // Data Retrieval
  // ***************************************************

  private updateTaskListModel(taskListSlice: TaskListSlice) {
    this.taskListModel.taskList =
      taskListSlice.taskList.map(
        taskResource => new TaskListRowModel(
          taskResource.createTime,
          taskResource.trackingId,
          taskResource.taskId,
          taskResource.name,
          taskResource.description,
          taskResource.assignee,
          this.formatAddress(taskResource.sender.address),
          this.formatAddress(taskResource.receiver.address))
      );
  }

  private formatAddress(address: Address): string {
    let formatedAddress = "";
    formatedAddress += address.street + " ";
    formatedAddress += address.streetNo + ", ";
    formatedAddress += address.zipCode + " ";
    formatedAddress += address.city;
    return formatedAddress;
  }
}
