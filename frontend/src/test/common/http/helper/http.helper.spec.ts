import {HttpHelper} from "../../../../app/common/http/helper/http.helper";
import {MockLocation} from "../../location.mock";

describe("HttpHelper", () => {

    let _httpHelper = new HttpHelper();

    it("Generates REST Api Url for localhost with Port 8080", () => {
        let location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "localhost";
        location.port="8080";
        expect(_httpHelper.getRestApiBaseUrlFromLocation(location)).toEqual("http://localhost:8081/educama/v1/");
    });
    it("Generates REST Api Url for localhost with Port 8090", () => {
        let location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "localhost";
        location.port="8090";
        expect(_httpHelper.getRestApiBaseUrlFromLocation(location)).toEqual("http://localhost:8091/educama/v1/");
    });
    it("Generates REST Api Url for http://educama-api-gateway.mybluemix.net/educama/v1/", () => {
        let location: Location = new MockLocation();
        location.protocol = "http:";
        location.host = "educama.mybluemix.net";
        expect(_httpHelper.getRestApiBaseUrlFromLocation(location)).toEqual("http://educama-api-gateway.mybluemix.net/educama/v1/");
    });
});
