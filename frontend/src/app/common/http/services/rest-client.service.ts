import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http, Response, Headers, RequestOptions, URLSearchParams} from "@angular/http";

import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";

import {HttpHelper} from "../helper/http.helper";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {AddErrorWithKeyAction, AddErrorWithTextAction} from "../../error/store/error.actions";
import * as _ from "lodash";

/*
 * Service to wrap REST HTTP calls and to provide a HAL-based API
 */
@Injectable()
export class RestClientService {

    private _baseUrl: string;
    private _headers: Headers;

    constructor(private _http: Http,
                private _httpHelper: HttpHelper,
                private _store: Store<State>) {
        this._baseUrl = this._httpHelper.getRestApiBaseUrl();
        this._headers = new Headers();
    }

    /**
     * Wrapper for HTTP GET operation
     */
    public get(url: string, paramsMap?: Map<any,any>): Observable<any> {
        this._headers.set("Cache-Control", "no-cache");
        let options = new RequestOptions({headers: this._headers});
        if (paramsMap) {
            let requestParams: URLSearchParams = new URLSearchParams();
            paramsMap.forEach((key, value) => {
                requestParams.set(key, value);
            });
            options = new RequestOptions({headers: this._headers, search: requestParams});
        }
        return this._http
            .get(this._baseUrl + url, options)
            .map(response => this.mapResponse(response))
            .catch(error => this.handleError(error));
    }

    /**
     * Wrapper for HTTP POST operation
     */
    public post(url: string, body?: string): Observable<any> {
        this._headers.set("Content-Type", "application/json");
        let options = new RequestOptions({headers: this._headers});
        if (body === null) body = "";
        return this._http
            .post(this._baseUrl + url, body, options)
            .map(response => this.mapResponse(response))
            .catch(error => this.handleError(error));
    }

    /**
     * Wrapper for HTTP PUT operation
     */
    public put(url: string, body?: string): Observable<any> {
        this._headers.set("Content-Type", "application/json");
        let options = new RequestOptions({headers: this._headers});
        if (body === null) body = "";
        let response = this._http
            .put(this._baseUrl + url, body, options)
            .map(response => this.mapResponse(response))
            .catch(error => this.handleError(error));
        return response;
    }

    /**
     * Generic response mapper. Since JSON is the object representation in java script,
     * the mappers returns type any. The invoker can cast the response.
     */
    private mapResponse(res: Response): any {
        let body: any = res.json();
        return body || {};
    }

    private handleError(error: any) {
        let errMsg: string
        if (error.status === 0) {
            errMsg = "REST-CLIENT-SERVICE_ERROR-SERVER-UNREACHABLE";
            this._store.dispatch(new AddErrorWithKeyAction(errMsg));
        } else {
            errMsg = error._body;
            this._store.dispatch(new AddErrorWithTextAction(errMsg));
        }
        return Observable.throw(errMsg);
    }

}