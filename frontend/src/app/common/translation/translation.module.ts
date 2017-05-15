import {NgModule, ModuleWithProviders} from "@angular/core";
import {HttpModule, Http} from "@angular/http";
import {
    TranslateModule,
    TranslateLoader,
    TranslateStaticLoader,
    MissingTranslationHandler, TranslatePipe
} from "ng2-translate/ng2-translate";
import {I18nDatePipe} from "./pipes/i18n-date.pipe";
import {TimeAgoPipe} from "./pipes/time-ago.pipe";
import {EducamaMissingTranslationHandler} from "./helper/educama-missing-translation-handler.helper";
import {TranslationNotifierService} from "./services/translation-notifier.service";

@NgModule({
    imports: [HttpModule, TranslateModule],
    declarations: [I18nDatePipe, TimeAgoPipe],
    exports: [TranslateModule, I18nDatePipe, TimeAgoPipe],
})
export class TranslationModule {
    static forRoot(): Array<ModuleWithProviders> {
        let modules: Array<ModuleWithProviders> =
            [
                {
                    ngModule: TranslationModule,
                    providers: [
                        {provide: MissingTranslationHandler, useClass: EducamaMissingTranslationHandler},
                        TranslationNotifierService
                    ]
                },
                TranslateModule.forRoot({
                    provide: TranslateLoader,
                    useFactory: (http: Http) => new TranslateStaticLoader(http, "resources/i18n", ".json"),
                    deps: [Http]
                })
            ];
        return modules;
    }
}