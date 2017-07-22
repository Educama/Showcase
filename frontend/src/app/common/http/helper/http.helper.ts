import {Injectable} from "@angular/core";
import {environment} from "../../../../environments/environment";

/*
 * Helper Class for HTTP related support functions
 */
@Injectable()
export class HttpHelper {

    static readonly BACKEND_RESOURCE_PATH = "/educama/v1/";

    /**
     * Provides the base URL used for Rest API calls to the backend.
     */
    public getRestApiBaseUrl(): string {
        const _location: Location = window.location;
        return environment.apiBaseUrl !== "" ?
          environment.apiBaseUrl + HttpHelper.BACKEND_RESOURCE_PATH :
          this.determineApiBaseUrlBasedOnFrontendUrl(_location);
    }

    /**
     * Determines the base URL from the front end URL
     */
    public determineApiBaseUrlBasedOnFrontendUrl(location: Location): string {
        return location.host.includes("localhost") ?
          location.protocol + "//localhost:" + (parseInt(location.port, 10) + 1) + "/educama/v1/" :
          location.protocol + "//" + location.host.replace("educama", "educama-api-gateway") + HttpHelper.BACKEND_RESOURCE_PATH;
    }
}
