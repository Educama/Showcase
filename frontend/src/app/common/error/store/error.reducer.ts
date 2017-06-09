import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./error.actions";
import {ERROR_SLICE_INITIAL_STATE} from "./error.initial-state";
import {ErrorSlice} from "./error.slice";

export const ERROR_REDUCER: ActionReducer<ErrorSlice> = (state: ErrorSlice = ERROR_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {

        case actions.ADD_ERROR_WITH_KEY:
            return Object.assign({}, state, {
                messageKey: action.payload,
                visible: true
            });

        case actions.ADD_ERROR_WITH_TEXT:
            return Object.assign({}, state, {
                messageText: action.payload,
                visible: true
            });

        case actions.INITIALIZE_ERROR_SLICE:
            return ERROR_SLICE_INITIAL_STATE;

        default:
            return state;
    }
};