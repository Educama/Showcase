import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./error.actions";
import {ERROR_SLICE_INITIAL_STATE} from "./error.initial-state";
import {ErrorSlice} from "./error.slice";

export function errorReducer(state: ErrorSlice = ERROR_SLICE_INITIAL_STATE, action: Action) {
    switch (action.type) {

        case actions.ADD_ERROR_WITH_KEY:
            const addErrorWithKeyAction = action as actions.AddErrorWithKeyAction;
            return Object.assign({}, state, {
                messageKey: addErrorWithKeyAction.payload,
                visible: true
            });

        case actions.ADD_ERROR_WITH_TEXT:
            const addErrorWithText = action as actions.AddErrorWithTextAction;
            return Object.assign({}, state, {
                messageText: addErrorWithText.payload,
                visible: true
            });

        case actions.INITIALIZE_ERROR_SLICE:
            return ERROR_SLICE_INITIAL_STATE;

        default:
            return state;
    }
}

export const ERROR_REDUCER: ActionReducer<ErrorSlice> = errorReducer;
