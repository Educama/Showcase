package org.educama.shipment.api.resource;

public class ShipmentResource {

    public long id;
    public String senderAddress;
    public String receiverAddress;

    /**
     * Creating a API-Model (Resource) instance from the internal data-model.
     * 
     * @param shipmentModel instance of the internal-data model
     * @return a converted ShipmentResource
     */
    public ShipmentResource fromShipment(org.educama.shipment.model.Shipment shipmentModel) {
        this.senderAddress = shipmentModel.senderAddress;
        this.receiverAddress = shipmentModel.receiverAddress;
        
        return this;
    }

    /**
     * Convert this instance of API-Model (Resource) to the internal data-model.
     * 
     * @return the converted instance
     */
    public org.educama.shipment.model.Shipment toShipment() {
        org.educama.shipment.model.Shipment toConvert = new org.educama.shipment.model.Shipment();
        toConvert.senderAddress = senderAddress;
        toConvert.receiverAddress = receiverAddress;

        return toConvert;
    }
}
