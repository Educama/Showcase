import {
  ModuleWithProviders,
  NgModule
} from "@angular/core";
import {
  MissingTranslationHandler,
  TranslateLoader,
  TranslateModule
} from "@ngx-translate/core";
import { I18nDatePipe } from "./pipes/i18n-date.pipe";
import { TimeAgoPipe } from "./pipes/time-ago.pipe";
import { EducamaMissingTranslationHandler } from "./helper/educama-missing-translation-handler.helper";
import { TranslationNotifierService } from "./services/translation-notifier.service";
import {
  HttpClient,
  HttpClientModule
} from "@angular/common/http";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";

@NgModule({
  imports: [
    HttpClientModule,
    TranslateModule
  ],
  declarations: [
    I18nDatePipe,
    TimeAgoPipe
  ],
  exports: [
    TranslateModule,
    I18nDatePipe,
    TimeAgoPipe
  ]
})
export class TranslationModule {
  public static forRoot(): Array<ModuleWithProviders> {
    return [
      {
        ngModule: TranslationModule,
        providers: [
          { provide: MissingTranslationHandler, useClass: EducamaMissingTranslationHandler },
          TranslationNotifierService
        ]
      },
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [ HttpClient ]
        }
      })
    ];
  }
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, "./assets/i18n/", ".json");
}


