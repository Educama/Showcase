import {Injectable} from "@angular/core";

/*
 * Helper Class for HTTP related support functions
 */
@Injectable()
export class HttpHelper {

    static readonly BACKEND_RESOURCE_PATH = "/educama/v1/";

    /*
     * Provides the base URL used for Rest API calls to the backend.
     */
    public getRestApiBaseUrl(): string {
        let _location: Location = window.location;
        return this.getRestApiBaseUrlFromLocation(_location);
    }

    /*
     * Provides the base URL (depending on the front end URL) for the REST API calls to the backend.
     */
    public getRestApiBaseUrlFromLocation(location: Location): string {
        if(location.host.includes("localhost")) {
            return location.protocol + "//localhost:" + (parseInt(location.port) + 1) + "/educama/v1/"
        } else {
            // Assuming cloud deployment
            return location.protocol + "//" + location.host.replace("educama", "educama-backend") + HttpHelper.BACKEND_RESOURCE_PATH;
        }
    }
}
