import {AirportResource} from "../api/airports/airport.resource";
import {AirportListSlice} from "./airport-list-page.slice";
import {SortOrder} from "../../../common/enums/sort-order.enum";

export const AIRPORT_LIST_SLICE_INITIAL_STATE: AirportListSlice = {
    pageNumber: 0,
    pageSize: 20,
    totalPages: 0,
    sortBy: "country",
    sortOrder: SortOrder.ascending,
    airportList: new Array<AirportResource>(),
    loading: false
};
