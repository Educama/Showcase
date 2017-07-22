import {Pipe, PipeTransform} from "@angular/core";
import * as moment from "moment";
import {BrowserLanguage} from "../helper/browser-language-scanner.helper";

const momentConstructor: (value?: any) => moment.Moment = (<any>moment).default || moment;

/*
 * Format the difference form a given ISO 8601 Java Date and the current time based on the browser language settings
 */
@Pipe({name: "educamaTimeAgo"})
export class TimeAgoPipe implements PipeTransform {

    /*
     * Standard transform operation for the pipe
     *
     * @param value ISO 8601 date string to be localized
     * @return difference between value and present time based on the browser language
     */
    public transform(value: string): string {
        if (!value) {
            return "";
        }
        const date = new Date(value);
        const momentInstance = momentConstructor(date);
        if (date instanceof Date) {
            momentInstance.locale(new BrowserLanguage().getShortBrowserLanguage());
            return momentInstance.fromNow();
        }
    }
}
