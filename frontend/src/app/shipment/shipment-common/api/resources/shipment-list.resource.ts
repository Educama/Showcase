import {ShipmentResource} from "./shipment.resource";
import {AbstractListResource} from "../../../../shared/api/resources/abstract-list.resource";

export class ShipmentListResource extends AbstractListResource {
    public shipments: ShipmentResource[];
}
