import {I18nDatePipe} from "../../../../app/common/translation/pipes/i18n-date.pipe";

describe("I18nDatePipe", () => {
    let pipe: I18nDatePipe;
    beforeEach(() => {
        pipe = new I18nDatePipe();
    });
    it("transforms \"2015-12-30\" to \"12/31/15\"", () => {
        expect(pipe.transform("2015-12-30")).toEqual("12/30/15");
    });
    it("transforms \"2016-01-12\" to \"12/01/16\"", () => {
        expect(pipe.transform("2016-01-12")).toEqual("01/12/16");
    });
    it("transforms \"2016-12-31\" to \"12/31/16\"", () => {
        expect(pipe.transform("2016-12-31")).toEqual("12/31/16");
    });
});