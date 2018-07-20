import {
  MissingTranslationHandler,
  MissingTranslationHandlerParams
} from "@ngx-translate/core";

/*
 * Educama specific implementation of the MissingTranslationHandler
 */
export class EducamaMissingTranslationHandler implements MissingTranslationHandler {

    public handle(param: MissingTranslationHandlerParams) {
        return "Translation missing for key " + param.key;
    }
}
