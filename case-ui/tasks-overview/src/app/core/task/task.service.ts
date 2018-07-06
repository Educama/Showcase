import { Injectable } from "@angular/core";

@Injectable()
export class TaskService {
  public getTasks(type: TaskType): Task[] {
    switch (type) {
      case TaskType.AVAILABLE:
        return [{
          label: "Task One",
          src: "http://localhost:4202"
        }, {
          label: "Task Two",
          src: "http://localhost:4202"
        }, {
          label: "Task Three",
          src: "http://localhost:4202"
        }];
      case TaskType.ACTIVE:
        return [{
          label: "Task Four",
          src: "http://localhost:4201"
        }, {
          label: "Task Five",
          src: "http://localhost:4201"
        }];
      case TaskType.COMPLETED:
        return [{
          label: "Task Six",
          src: ""
        }, {
          label: "Task Seven",
          src: ""
        }, {
          label: "Task Eight",
          src: ""
        }, {
          label: "Task Nine",
          src: ""
        }];
    }
  }
}

export enum TaskType {
  AVAILABLE, ACTIVE, COMPLETED
}

export interface Task {
  label: string;
  src: string;
}
