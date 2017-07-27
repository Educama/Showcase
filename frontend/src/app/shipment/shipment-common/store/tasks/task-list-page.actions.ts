import {Action} from "@ngrx/store";
import {TaskResource} from "../../api/resources/task.resource";

// Action Types
export const LOAD_TASKS = "LOAD_TASKS";

// Actions
export class LoadTasksAction implements Action {
    type = LOAD_TASKS;

    constructor(public payload: TaskResource[]) {
    }
}
