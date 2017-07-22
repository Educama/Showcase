import {HttpHelper} from "./http.helper";
import {MockLocation} from "../mock/location.mock";

describe("HttpHelper", () => {

    const _httpHelper = new HttpHelper();

    it("Generates REST Api Url for localhost with Port 8080", () => {
        const location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "localhost";
        location.port = "8080";
        expect(_httpHelper.determineApiBaseUrlBasedOnFrontendUrl(location)).toEqual("http://localhost:8081/educama/v1/");
    });
    it("Generates REST Api Url for localhost with Port 8090", () => {
        const location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "localhost";
        location.port = "8090";
        expect(_httpHelper.determineApiBaseUrlBasedOnFrontendUrl(location)).toEqual("http://localhost:8091/educama/v1/");
    });
    it("Generates REST Api Url for http://educama-api-gateway.mybluemix.net/educama/v1/", () => {
        const location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "educama.mybluemix.net";
        expect(_httpHelper.determineApiBaseUrlBasedOnFrontendUrl(location)).toEqual("http://educama-api-gateway.mybluemix.net/educama/v1/");
    });
});
