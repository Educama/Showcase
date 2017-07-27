import {Action} from "@ngrx/store";
import {ShipmentResource} from "../../api/resources/shipment.resource";
import {ShipmentListResource} from "../../api/resources/shipment-list.resource";

// Initialize Actions
export const INITIALIZE_SHIPMENT_LIST = "INITIALIZE_SHIPMENT_LIST";

// Action Types
export const LOAD_SINGLE_SHIPMENT = "LOAD_SINGLE_SHIPMENT";

// Shipments API Actions
export const REQUEST_SHIPMENTS = "REQUEST_SHIPMENTS";
export const REQUEST_SHIPMENTS_SUCCESSFUL = "REQUEST_SHIPMENTS_SUCCESSFUL";
export const REQUEST_SHIPMENTS_FAILED = "REQUEST_SHIPMENTS_FAILED";

export class LoadSingleShipmentAction implements Action {
    type = LOAD_SINGLE_SHIPMENT;

    constructor(public payload: ShipmentResource) {
    }
}

export class InitializeShipmentListAction implements Action {
  type = INITIALIZE_SHIPMENT_LIST;

  constructor() {
  }
}

export class RequestShipmentsAction implements Action {
  type = REQUEST_SHIPMENTS;

  constructor() {
  }
}

export class RequestShipmentsSuccessfulAction implements Action {
  type = REQUEST_SHIPMENTS_SUCCESSFUL;

  constructor(public payload: ShipmentListResource) {
  }
}

export class RequestShipmentsFailedAction implements Action {
  type = REQUEST_SHIPMENTS_FAILED;

  constructor() {
  }
}
