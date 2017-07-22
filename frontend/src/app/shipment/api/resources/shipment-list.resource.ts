import {ShipmentResource} from "./shipment.resource";
import {AbstractListResource} from "../../../common/api/resources/abstract-list.resource";

export class ShipmentListResource extends AbstractListResource {
    public shipments: ShipmentResource[];
}
