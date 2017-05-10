import {NgModule, ModuleWithProviders} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ErrorModule} from "./error/error.module";
import {RestModule} from "./http/rest.module";
import {TranslationModule} from "./translation/translation.module";
import {UIModule} from "./ui/ui.module";
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ErrorModule,
        RestModule,
        TranslationModule,
        UIModule
    ]
})
export class SharedModule {
    static forRoot(): Array<ModuleWithProviders> {
        let modules: Array<ModuleWithProviders | Array<ModuleWithProviders>> =
            [
                ErrorModule.forRoot(),
                RestModule.forRoot(),
                TranslationModule.forRoot(),
                UIModule.forRoot()
            ]
        return [].concat(...modules);
    }
}