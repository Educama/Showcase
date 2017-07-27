import {Component, OnDestroy, OnInit} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import {TaskListModel, TaskListRowModel} from "./task-list-page.model";
import {State} from "../../../app.reducers";
import {Address} from "../../../customer/customer-common/api/datastructures/address.datastructure";
import {Router} from "@angular/router";
import {TaskListSlice} from "../../shipment-common/store/tasks/task-list-page.slice";
import {InitializeTaskListAction, RequestTasksAction} from "../../shipment-common/store/tasks/task-list-page.actions";

@Component({
    selector: "educama-task-list-page",
    templateUrl: `./task-list-page.component.html`
})
export class TaskListPageComponent implements OnInit, OnDestroy {

    // relevant slice of store and subscription for this slice
    public taskListSlice: Observable<TaskListSlice>;
    public taskListSliceSubscription: Subscription;

    // model for the page
    public taskListModel: TaskListModel = new TaskListModel();

    constructor(private _router: Router,
                private _store: Store<State>) {

        this.taskListSlice = this._store.select(state => state.taskListSlice);
        this.taskListSliceSubscription = this.taskListSlice
            .subscribe(taskListSlice => this.updateTaskListModel(taskListSlice));
    }

    public ngOnInit() {
        this._store.dispatch(new RequestTasksAction());
    }

    public ngOnDestroy() {
        this._store.dispatch(new InitializeTaskListAction());
        this.taskListSliceSubscription.unsubscribe();
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    public onTaskSelectedEvent(trackingId: string) {
        this._router.navigate(["/shipments/edit/" + trackingId]);
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
