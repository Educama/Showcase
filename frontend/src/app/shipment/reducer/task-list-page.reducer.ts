import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./task-list-page.actions";
import {TaskResource} from "../api/resources/task.resource";

export interface TaskListSlice {
    taskList: TaskResource[];
}

export const TASK_LIST_SLICE_INITIAL_STATE: TaskListSlice = {
    taskList: []
};

export function taskListPageReducer(state: TaskListSlice = TASK_LIST_SLICE_INITIAL_STATE,
                                    action: Action): TaskListSlice {
    switch (action.type) {
      case actions.LOAD_TASKS:
        const loadTasksAction = action as actions.LoadTasksAction;
        return Object.assign({}, state, {
          taskList: loadTasksAction.payload
        });
      default:
        return state;
    }
  }

export const TASK_LIST_PAGE_REDUCER: ActionReducer<TaskListSlice> = taskListPageReducer;
