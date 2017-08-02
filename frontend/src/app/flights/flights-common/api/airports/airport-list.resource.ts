import {AbstractListResource} from "../../../../shared/api/resources/abstract-list.resource";
import {AirportResource} from "./airport.resource";

export class AirportListResource extends AbstractListResource {
    public content: AirportResource[];
}
