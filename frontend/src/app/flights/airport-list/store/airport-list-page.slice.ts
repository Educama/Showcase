import {AirportResource} from "../../api/airports/airport.resource";
import {SortOrder} from "../../../common/enums/sort-order.enum";

export interface AirportListSlice {
    pageNumber: number;
    pageSize: number;
    totalPages: number;
    sortBy: string;
    sortOrder: SortOrder;
    airportList: AirportResource[];
    loading: boolean;
}
