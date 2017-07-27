import {ActionReducer, Action} from "@ngrx/store";
import * as actions from "./task-list-page.actions";
import {TASK_LIST_SLICE_INITIAL_STATE} from "./task-list-page.initial-state";
import {TaskListSlice} from "./task-list-page.slice";

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
