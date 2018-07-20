import { Injectable } from "@angular/core";
import { RestClientService } from "../../../shared/http/services/rest-client.service";
import { Observable } from "rxjs";
import { TaskListResource } from "./resources/task-list.resource";

/*
 * Service to communicate with Task Resource
 */
@Injectable()
export class TaskService {

  private TASK_RESOURCE_PATH = "tasks";

  constructor(private _restClientService: RestClientService) {
  }

  /*
   * Find all tasks
   *
   * @return An observable array of all active tasks
   */
  public findTasks(): Observable<TaskListResource> {
    return this._restClientService.get(this.TASK_RESOURCE_PATH + "/active");
  }

  /*
   * Find all active tasks for a shipment
   *
   * @return An observable array of all active tasks
   */
  public findTasksForShipment(trackingId: string): Observable<TaskListResource> {
    return this._restClientService.get(this.TASK_RESOURCE_PATH + "/active/" + trackingId);
  }

  /*
   * Find all enabled tasks
   *
   * @return An observable array of all enabled tasks
   */
  public findAllEnabledTasks(): Observable<TaskListResource> {
    return this._restClientService.get(this.TASK_RESOURCE_PATH + "/enabled");
  }

  /*
   * Find all enabled tasks for a specific shipment
   *
   * @return An observable array of all enabled tasks for a shipment
   */
  public findEnabledTasksToShipment(trackingId: string): Observable<TaskListResource> {
    return this._restClientService.get(this.TASK_RESOURCE_PATH + "/enabled/" + trackingId);
  }

  /*
  * Find all completed tasks for a specific shipment
  *
  * @return An observable array of all enabled tasks for a shipment
  */
  public findCompletedTasksForShipment(trackingId: string): Observable<TaskListResource> {
    return this._restClientService.get(this.TASK_RESOURCE_PATH + "/completed/" + trackingId);
  }

  /*
  * Start a enabled task
  *
  * @return An observable array of all remaining enabled tasks for a shipment
  */
  public manuallyStartEnabledTask(trackingId: string, name: string): Observable<TaskListResource> {
    return this._restClientService.post(this.TASK_RESOURCE_PATH + "/enabled/start/" + trackingId + "/" + name);
  }

  /*
  * Complete a task by its name
  *
  * @return An observable array of all remaining active tasks for a shipment
  */
  public manualyCompleteTaskByName(trackingId: string, name: string): Observable<TaskListResource> {
    return this._restClientService.post(this.TASK_RESOURCE_PATH + "/active/complete/" + trackingId + "/" + name);
  }
}
