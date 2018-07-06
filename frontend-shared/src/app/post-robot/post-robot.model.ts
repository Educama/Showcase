import { ZalgoPromise } from "zalgo-promise/src";

export interface PostRobotData<T> {
  data: T;
  origin: string;
  source: Window;
}

export interface PostRobot {
  on: (name: string,
       options: { domain: string },
       handler: (event: PostRobotData<any>) => any)
    => void;
  send: (window: Window,
         name: string, data: {},
         options: { domain: string })
    => ZalgoPromise<any>;
  CONFIG: any;
}
