export class BrowserLanguage {

    private _shortBrowserLanguage: string;
    private _fullBrowserLanguage: string;

    /**
     * Retrieve the full browser language and try to split off the langugage part behind the dash.
     * If the split off part doesn't match with 'de' or 'en' the language is set default to en-US.
     */
    private getBrowserLanguage() {
        this._fullBrowserLanguage = navigator.language;
        this._shortBrowserLanguage = this._fullBrowserLanguage.split("-")[0]; // use navigator lang if available
        if (!/(de|en)/gi.test(this._shortBrowserLanguage)) {
            this._fullBrowserLanguage = "en-US";
            this._shortBrowserLanguage = "en";
        }
    }

    constructor() {
        this.getBrowserLanguage();
    }

    /**
     * Retrieve the full browser language string
     * @returns Full browser language string
     */
    public getFullBrowserLanguage() {
        return this._fullBrowserLanguage;
    }

    /**
     * Retrieve the first part from the browser language string
     * @returns Short browser language string
     */
    public getShortBrowserLanguage() {
        return this._shortBrowserLanguage;
    }
}
