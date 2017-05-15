import {NgModule, ModuleWithProviders} from "@angular/core";
import {CommonModule} from "@angular/common";
import {TranslationModule} from "../translation/translation.module";
import {UIModule} from "../ui/ui.module";
import {ErrorComponent} from "./components/error.component";
import {ErrorService} from "./services/error.service";

@NgModule({
    imports: [CommonModule, TranslationModule, UIModule],
    declarations: [ErrorComponent],
    exports: [ErrorComponent],
})
export class ErrorModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ErrorModule,
            providers: [ErrorService]
        };
    }
}