import {MissingTranslationHandler} from "ng2-translate/ng2-translate";

/*
 * Educama specific implementation of the MissingTranslationHandler
 */
export class EducamaMissingTranslationHandler implements MissingTranslationHandler {

    public handle(key: string) {
        return key + " is missing";
    }
}