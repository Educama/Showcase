import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./task-list-page.actions";
import {TASK_LIST_SLICE_INITIAL_STATE} from "./task-list-page.initial-state";
import {TaskListSlice} from "./task-list-page.slice";

export function taskListPageReducer(state: TaskListSlice = TASK_LIST_SLICE_INITIAL_STATE,
                                    action: Action): TaskListSlice {
    switch (action.type) {
      case actions.INITIALIZE_TASK_LIST:
        return TASK_LIST_SLICE_INITIAL_STATE;
      case actions.REQUEST_TASKS:
        return Object.assign({}, state, {
          loading: true
        });
      case actions.REQUEST_TASKS_SUCCESSFUL:
        const requestTasksSuccessfulAction = action as actions.RequestTasksSuccessfulAction;
        return Object.assign({}, state, {
          taskList: requestTasksSuccessfulAction.payload.tasks,
          loading: false
        });
      case actions.REQUEST_TASKS_FAILED:
        return Object.assign({}, state, {
          loading: false
        });
      default:
        return state;
    }
  }

export const TASK_LIST_PAGE_REDUCER: ActionReducer<TaskListSlice> = taskListPageReducer;
