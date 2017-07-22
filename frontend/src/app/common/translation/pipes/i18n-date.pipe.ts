import {Pipe, PipeTransform} from "@angular/core";
import {BrowserLanguage} from "../helper/browser-language-scanner.helper";

/*
 * Format a ISO 8601 Java Date based on the browser language settings
 */
@Pipe({name: "educamaI18nDateFormat"})
export class I18nDatePipe implements PipeTransform {

    static _ALIASES = {
        shortDate: {day: "2-digit", month: "2-digit", year: "2-digit"},
        fullDateAndTime: {day: "2-digit", month: "2-digit", year: "numeric", hour: "2-digit", minute: "2-digit"},
        shortTime: {hour: "2-digit", minute: "2-digit"}
    };

    /*
     * Date pipeline to transform Java ISO8601 strings.
     * The pipe accepts parameters to use predefined date formats.
     * This date formats can contain weekday, year, month, day, hour, minute and second.
     * Default format displays year, month and day in 2-digit format
     *
     * @param value ISO 8601 date string to be localized
     * @return localized date based on the browser language
     */
    public transform(value: string, pattern: string = "shortDate"): string {
        let _options = {};
        if (!value) {
            return "";
        }
        if (I18nDatePipe._ALIASES.hasOwnProperty(pattern)) {
            _options = I18nDatePipe._ALIASES[pattern];
        }
        return new Intl.DateTimeFormat(new BrowserLanguage().getFullBrowserLanguage(), _options).format(Date.parse(value));
    }
}
