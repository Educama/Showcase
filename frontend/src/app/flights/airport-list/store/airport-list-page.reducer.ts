import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./airport-list-page.actions";
import {AIRPORT_LIST_SLICE_INITIAL_STATE} from "./airport-list-page.initial-state";
import {AirportListSlice} from "./airport-list-page.slice";

export const AIRPORT_LIST_PAGE_REDUCER: ActionReducer<AirportListSlice> = (state: AirportListSlice = AIRPORT_LIST_SLICE_INITIAL_STATE, action: Action) => {
    switch (action.type) {

        case actions.INITIALIZE_AIRPORT_LIST:
            return AIRPORT_LIST_SLICE_INITIAL_STATE;

        case actions.CHANGE_AIRPORTS_PAGE:
            return Object.assign({}, state, {
                pageSize: action.payload.pageSize,
                pageNumber: action.payload.pageNumber
            });

        case actions.CHANGE_AIRPORTS_SORTING:
            return Object.assign({}, state, {
                sortBy: action.payload.sortBy,
                sortOrder: action.payload.sortOrder
            });

        case actions.REQUEST_AIRPORTS:
            return Object.assign({}, state, {
                loading: true
            });

        case actions.REQUEST_AIRPORTS_SUCCESSFUL:
            return Object.assign({}, state, {
                airportList: action.payload.content,
                totalPages: action.payload.totalPages,
                loading: false
            });

        case actions.REQUEST_AIRPORTS_FAILED:
            return Object.assign({}, state, {
                loading: false
            });

        default:
            return state;
    }
};