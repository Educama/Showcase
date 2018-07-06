import {
    ModuleWithProviders,
    NgModule
} from "@angular/core";
import { WindowRefService } from "./window-ref/window-ref.service";
import { PostRobotService } from "./post-robot/post-robot.service";

@NgModule({
    imports: [],
    exports: [],
    declarations: [],
    entryComponents: []
})
export class SharedModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedModule,
            providers: [
                WindowRefService,
                PostRobotService
            ]
        };
    }

}

