import {ShipmentResource} from "../api/resources/shipment.resource";

export class ShipmentListModel {
    public shipmentList: ShipmentListRowModel[];
}

export class ShipmentListRowModel {
    public trackingId: string;
    public senderAddress: string;
    public receiverAddress: string;

    constructor(trackingId: string, senderAddress: string, receiverAddress: string) {
        this.trackingId = trackingId;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }
}
