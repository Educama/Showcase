import {ShipmentResource} from "../api/resources/shipment.resource";

export class ShipmentListModel {
    public shipmentList: ShipmentListRowModel[];
}

export class ShipmentListRowModel {
    public trackingId: number;
    public senderAddress: string;
    public receiverAddress: string;

    constructor(trackingId: number, senderAddress: string, receiverAddress: string) {
        this.trackingId = trackingId;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }
}
