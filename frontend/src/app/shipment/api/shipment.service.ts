import {Injectable} from "@angular/core";
import {RestClientService} from "../../common/http/services/rest-client.service";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";
import {ShipmentListResource} from "./resources/shipment-list.resource";
import {ShipmentResource} from "./resources/shipment.resource";

/*
 * Service to communicate with Shipments Resource
 */
@Injectable()
export class ShipmentService {

    private SHIPMENT_RESOURCE_PATH = "shipments";

    constructor(private _restClientService: RestClientService) {
    }

    /*
     * Create a new shipment
     *
     * @param shipment The shipment to be created
     * @return An observable of a shipment
     */
    public createShipment(shipment: ShipmentResource): Observable<ShipmentResource> {
        return this._restClientService.post(this.SHIPMENT_RESOURCE_PATH, JSON.stringify(shipment));
    }

    /*
     * Find all shipments
     *
     * @return An observable array of shipments
     */
    public findShipments(): Observable<ShipmentListResource> {
        return this._restClientService.get(this.SHIPMENT_RESOURCE_PATH);
    }

    /*
     * Update a shipment
     * @param shipment The Shipment to be updated
     * @return An observable of a shipment
     */
    public updateShipment(trackingId: string, shipment: ShipmentResource): Observable<ShipmentResource> {
        return this._restClientService.put(this.SHIPMENT_RESOURCE_PATH + "/" + trackingId, JSON.stringify(shipment));
    }
    /*
     * Find a single shipment
     */
    public findShipmentbyId(trackingid: string): Observable<ShipmentResource> {
        return this._restClientService.get(this.SHIPMENT_RESOURCE_PATH + "/" + trackingid);
    }
}
