import {TaskResource} from "./task.resource";
import {AbstractListResource} from "../../../../shared/api/resources/abstract-list.resource";

export class TaskListResource extends AbstractListResource {
    public tasks: TaskResource[];
}
