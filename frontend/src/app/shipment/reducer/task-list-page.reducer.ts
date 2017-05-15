import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./task-list-page.actions";
import {TaskResource} from "../api/resources/task.resource";

export interface TaskListSlice {
    taskList: TaskResource[]
}

export const TASK_LIST_SLICE_INITIAL_STATE: TaskListSlice = {
    taskList: []
};

export const TASK_LIST_PAGE_REDUCER: ActionReducer<TaskListSlice> =
    (state: TaskListSlice = TASK_LIST_SLICE_INITIAL_STATE, action: Action) => {
        switch (action.type) {
            case actions.LOAD_TASKS:
                return Object.assign({}, state, {
                    taskList: action.payload
                });
            default:
                return state;
        }
    };