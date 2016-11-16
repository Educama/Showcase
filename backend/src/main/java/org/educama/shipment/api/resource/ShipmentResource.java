package org.educama.shipment.api.resource;


public class ShipmentResource {

    public long id;
    public String senderAdress;
    public String receiverAdress;

    public ShipmentResource() {

        this.senderAdress = "undefined";
        this.receiverAdress = "undefined";
    }

    public ShipmentResource (org.educama.shipment.model.Shipment shipmentModel) {

        this.senderAdress = shipmentModel.senderAdress;
        this.receiverAdress = shipmentModel.receiverAdress;
    }

    public org.educama.shipment.model.Shipment toShipment() {

        org.educama.shipment.model.Shipment toConvert = new org.educama.shipment.model.Shipment();
        toConvert.senderAdress = senderAdress;
        toConvert.receiverAdress = receiverAdress;

        return toConvert;
    }
}