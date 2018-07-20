import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { RestClientService } from "../../../../shared/http/services/rest-client.service";
import { SortOrder } from "../../../../shared/enums/sort-order.enum";
import { AirlineListResource } from "./airline-list.resource";
import { AirlineSuggestionsResource } from "./airline-suggestions.resource";

/*
 * Service to communicate with Customer Resource
 */
@Injectable()
export class AirlineService {

  private AIRLINES_RESOURCE_PATH = "airlines";
  private AIRLINE_SUGGESTIONS_RESOURCE_PATH: string = this.AIRLINES_RESOURCE_PATH + "/suggestions";

  constructor(private _restClientService: RestClientService) {
  }

  /*
   * Find all airlines
   *
   * @return An observable array of airlines
   */
  public findAirlines(pageNumber: number, pageSize: number, sortBy?: string, sortOder?: SortOrder): Observable<AirlineListResource> {
    const pageNumberText = "page=" + pageNumber;
    const pageSizeText = "size=" + pageSize;
    let sortText = "sort=" + sortBy;
    sortText = sortOder === SortOrder.ascending ? sortText + ",asc" : sortText;
    sortText = sortOder === SortOrder.desending ? sortText + ",desc" : sortText;
    return this._restClientService.get(this.AIRLINES_RESOURCE_PATH + "?" + pageNumberText + "&" + pageSizeText + "&" + sortText);
  }

  /*
   * Find suggestions for airlines based on a search term
   *
   * @return An observable array of airlines
   */
  public findAirlineSuggestions(term: string, pageNumber?: number, pageSize?: number): Observable<AirlineSuggestionsResource> {
    return this._restClientService.get(this.AIRLINE_SUGGESTIONS_RESOURCE_PATH + "?term=" + term);
  }
}
