import {Action} from "@ngrx/store";
import {AirportListResource} from "../api/airports/airport-list.resource";
import {SortOrder} from "../../../common/enums/sort-order.enum";

// Initialize Actions
export const INITIALIZE_AIRPORT_LIST = "INITIALIZE_AIRPORT_LIST";

// Customer List Actions
export const CHANGE_AIRPORTS_PAGE = "CHANGE_AIRPORTS_PAGE";
export const CHANGE_AIRPORTS_SORTING = "CHANGE_AIRPORTS_SORTING";

// Customer API Actions
export const REQUEST_AIRPORTS = "REQUEST_AIRPORTS";
export const REQUEST_AIRPORTS_SUCCESSFUL = "REQUEST_AIRPORTS_SUCCESSFUL";
export const REQUEST_AIRPORTS_FAILED = "REQUEST_AIRPORTS_FAILED";

export class InitializeAirportListAction implements Action {
    type = INITIALIZE_AIRPORT_LIST;

    constructor() {
    }
}

export class ChangeAirportsPageAction implements Action {
    type = CHANGE_AIRPORTS_PAGE;

    constructor(public payload: { pageNumber: number, pageSize: number }) {
    }
}

export class ChangeAirportsSortingAction implements Action {
    type = CHANGE_AIRPORTS_SORTING;

    constructor(public payload: { sortBy: string, sortOrder: SortOrder }) {
    }
}

export class RequestAirportsAction implements Action {
    type = REQUEST_AIRPORTS;

    constructor() {
    }
}

export class RequestAirportsSuccessfulAction implements Action {
    type = REQUEST_AIRPORTS_SUCCESSFUL;

    constructor(public payload: AirportListResource) {
    }
}

export class RequestAirportsFailedAction implements Action {
    type = REQUEST_AIRPORTS_FAILED;

    constructor() {
    }
}
