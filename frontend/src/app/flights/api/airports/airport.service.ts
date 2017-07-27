import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";
import {RestClientService} from "../../../common/http/services/rest-client.service";
import {AirportListResource} from "./airport-list.resource";
import {AirportSuggestionsResource} from "./airport-suggestions.resource";
import {SortOrder} from "../../../common/enums/sort-order.enum";

/*
 * Service to communicate with Customer Resource
 */
@Injectable()
export class AirportService {

    private AIRPORTS_RESOURCE_PATH:string = "airports";
    private AIRPORT_SUGGESTIONS_RESOURCE_PATH:string = this.AIRPORTS_RESOURCE_PATH + "/suggestions";

    constructor(private _restClientService: RestClientService) {
    }

    /*
     * Find all airports
     *
     * @return An observable array of airports
     */
    public findAirports(pageNumber: number, pageSize: number, sortBy?: string, sortOder?: SortOrder): Observable<AirportListResource> {
        let pageNumberText = "page=" + pageNumber;
        let pageSizeText = "size=" + pageSize;
        let sortText = "sort=" + sortBy;
        sortText = sortOder === SortOrder.ascending ? sortText + ",asc" : sortText;
        sortText = sortOder === SortOrder.desending ? sortText + ",desc" : sortText;
        return this._restClientService.get(this.AIRPORTS_RESOURCE_PATH + "?" + pageNumberText + "&" + pageSizeText + "&" + sortText);
    }

    /*
     * Find suggestions for airports based on a search term
     *
     * @return An observable array of airports
     */
    public findAirportSuggestions(term: string, pageNumber?: number, pageSize?: number): Observable<AirportSuggestionsResource> {
        return this._restClientService.get(this.AIRPORT_SUGGESTIONS_RESOURCE_PATH + "?term=" + term);
    }
}
