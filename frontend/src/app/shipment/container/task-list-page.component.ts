import {Component, OnInit, OnDestroy} from "@angular/core";
import {Store} from "@ngrx/store";
import {Observable, Subscription} from "rxjs";
import * as actions from "../reducer/task-list-page.actions";
import {ErrorService} from "../../common/error/services/error.service";
import {TaskService} from "../api/task.service";
import {TaskListSlice} from "../reducer/task-list-page.reducer";
import {TaskListModel} from "./task-list-page.model";
import {State} from "../../app.reducers";
import {TaskResource} from "../api/resources/task.resource";

@Component({
    selector: "educama-task-list-page",
    templateUrl: `./task-list-page.component.html`
})
export class TaskListPageComponent implements OnInit, OnDestroy{

    // relevant slice of store and subscription for this slice
    public taskListSlice: Observable<TaskListSlice>;
    public taskListSliceSubscription: Subscription;

    // model for the page
    public taskListModel: TaskListModel = new TaskListModel();

    public selectedTask: TaskResource = new TaskResource();

    constructor(private _errorService: ErrorService,
                private _taskService: TaskService,
                private _store: Store<State>) {

        this.taskListSlice = this._store.select(state => state.taskListSlice);
        this.taskListSliceSubscription = this.taskListSlice
            .subscribe(taskListSlice => this.updateTaskListModel(taskListSlice))
    }

    public ngOnInit() {
        this.loadTasks();
    }

    public ngOnDestroy() {
        this.taskListSliceSubscription.unsubscribe();
    }

    // ***************************************************
    // Event Handler
    // ***************************************************

    /*
     * Handles the error events from components
     */
    public onErrorEvent(errorMessage: string) {
        this._errorService.showError(errorMessage);
    }

    // ***************************************************
    // Data Retrieval
    // ***************************************************

    private loadTasks() {
        this._taskService.findTasks()
            .subscribe(
                taskListResource => this._store.dispatch(new actions.LoadTasksAction(taskListResource.tasks)),
                null
            );
    }

    private updateTaskListModel(taskListSlice: TaskListSlice) {
        this.taskListModel.taskList = taskListSlice.taskList;
    }
}