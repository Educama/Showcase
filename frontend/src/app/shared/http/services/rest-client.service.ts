import {
  Observable,
  throwError as observableThrowError
} from "rxjs";

import { catchError } from "rxjs/operators";
import { Injectable } from "@angular/core";

import { HttpHelper } from "../helper/http.helper";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import {
  AddErrorWithKeyAction,
  AddErrorWithTextAction
} from "../../error/store/error.actions";
import {
  HttpClient,
  HttpHeaders,
  HttpParams
} from "@angular/common/http";

/*
 * Service to wrap REST HTTP calls and to provide a HAL-based API
 */
@Injectable()
export class RestClientService {

  private _baseUrl: string;
  private _headers: HttpHeaders;

  constructor(private _http: HttpClient,
              private _httpHelper: HttpHelper,
              private _store: Store<State>) {
    this._baseUrl = this._httpHelper.getRestApiBaseUrl();
    this._headers = new HttpHeaders();
  }

  /**
   * Wrapper for HTTP GET operation
   */
  public get(url: string, paramsMap?: Map<any, any>): Observable<any> {
    this._headers.set("Cache-Control", "no-cache");
    let options: any = { headers: this._headers };
    if (paramsMap) {
      const requestParams: HttpParams = new HttpParams();
      paramsMap.forEach((key, value) => {
        requestParams.set(key, value);
      });
      options = { headers: this._headers, params: requestParams };
    }
    return this._http
               .get<any>(this._baseUrl + url, options)
               .pipe(
                 catchError(error => this.handleError(error))
               );
  }

  /**
   * Wrapper for HTTP POST operation
   */
  public post(url: string, body?: string): Observable<any> {
    this._headers.set("Content-Type", "application/json");
    const options = { headers: this._headers };
    if (body === null) {
      body = "";
    }
    return this._http
               .post<any>(this._baseUrl + url, body, options)
               .pipe(
                 catchError(error => this.handleError(error))
               );
  }

  /**
   * Wrapper for HTTP PUT operation
   */
  public put(url: string, body?: string): Observable<any> {
    this._headers.set("Content-Type", "application/json");
    const options = { headers: this._headers };
    if (body === null) {
      body = "";
    }
    return this._http
               .put<any>(this._baseUrl + url, body, options)
               .pipe(
                 catchError(error => this.handleError(error))
               );
  }

  private handleError(error: any) {
    let errMsg: string;
    if (error.status === 0) {
      errMsg = "REST-CLIENT-SERVICE_ERROR-SERVER-UNREACHABLE";
      this._store.dispatch(new AddErrorWithKeyAction(errMsg));
    } else {
      errMsg = error._body;
      this._store.dispatch(new AddErrorWithTextAction(errMsg));
    }
    return observableThrowError(errMsg);
  }

}
