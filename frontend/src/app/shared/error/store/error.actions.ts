import {Action} from "@ngrx/store";

export const INITIALIZE_ERROR_SLICE = "INITIALIZE_ERROR_SLICE";
export const ADD_ERROR_WITH_KEY = "ADD_ERROR_WITH_KEY";
export const ADD_ERROR_WITH_TEXT = "ADD_ERROR_WITH_TEXT";

export class InitializeErrorSliceAction implements Action {
    type = INITIALIZE_ERROR_SLICE;

    constructor() {
    }
}

export class AddErrorWithKeyAction implements Action {
    type = ADD_ERROR_WITH_KEY;

    constructor(public payload: string) {
    }
}

export class AddErrorWithTextAction implements Action {
    type = ADD_ERROR_WITH_TEXT;

    constructor(public payload: string) {
    }
}
