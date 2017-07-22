import {Action, ActionReducer} from "@ngrx/store";
import * as actions from "./airport-list-page.actions";
import {AIRPORT_LIST_SLICE_INITIAL_STATE} from "./airport-list-page.initial-state";
import {AirportListSlice} from "./airport-list-page.slice";

export function airportListPageReducer(state: AirportListSlice = AIRPORT_LIST_SLICE_INITIAL_STATE, action: Action) {
    switch (action.type) {

        case actions.INITIALIZE_AIRPORT_LIST:
            return AIRPORT_LIST_SLICE_INITIAL_STATE;

        case actions.CHANGE_AIRPORTS_PAGE:
            const changeAirportsPageAction = action as actions.ChangeAirportsPageAction;
            return Object.assign({}, state, {
                pageSize: changeAirportsPageAction.payload.pageSize,
                pageNumber: changeAirportsPageAction.payload.pageNumber
            });

        case actions.CHANGE_AIRPORTS_SORTING:
            const changeAirportsSortingAction = action as actions.ChangeAirportsSortingAction;
            return Object.assign({}, state, {
                sortBy: changeAirportsSortingAction.payload.sortBy,
                sortOrder: changeAirportsSortingAction.payload.sortOrder
            });

        case actions.REQUEST_AIRPORTS:
            return Object.assign({}, state, {
                loading: true
            });

        case actions.REQUEST_AIRPORTS_SUCCESSFUL:
            const requestAirportsSuccessfulAction = action as actions.RequestAirportsSuccessfulAction;
            return Object.assign({}, state, {
                airportList: requestAirportsSuccessfulAction.payload.content,
                totalPages: requestAirportsSuccessfulAction.payload.totalPages,
                loading: false
            });

        case actions.REQUEST_AIRPORTS_FAILED:
            return Object.assign({}, state, {
                loading: false
            });

        default:
            return state;
    }
}

export const AIRPORT_LIST_PAGE_REDUCER: ActionReducer<AirportListSlice> = airportListPageReducer;
