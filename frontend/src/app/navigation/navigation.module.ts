import {NgModule} from "@angular/core";
import {NavigationBarComponent} from "./components/navigation-bar.component";
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
      SharedModule
    ],
    declarations: [
      NavigationBarComponent
    ],
    exports: [
      NavigationBarComponent
    ]
})
export class NavigationModule {
}
