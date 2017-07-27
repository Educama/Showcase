import {TaskResource} from "../../api/resources/task.resource";

export interface TaskListSlice {
  taskList: TaskResource[];
  loading: boolean;
}
