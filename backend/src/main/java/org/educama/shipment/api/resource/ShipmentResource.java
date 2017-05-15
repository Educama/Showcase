package org.educama.shipment.api.resource;

import org.educama.shipment.model.Shipment;

/**
 * REST-Resource for single shipment.
 */
public class ShipmentResource {

    public String trackingId;
    public String customer;
    public String senderAddress;
    public String receiverAddress;

    /**
     * Create a API-Model (Resource) instance from the internal data model.
     *
     * @param shipmentModel instance of the internal-data model
     * @return a converted ShipmentResource
     */
    public ShipmentResource fromShipment(Shipment shipmentModel) {
        this.trackingId = shipmentModel.trackingId;
        this.customer = shipmentModel.customer;
        this.senderAddress = shipmentModel.senderAddress;
        this.receiverAddress = shipmentModel.receiverAddress;

        return this;
    }

    /**
     * Convert this instance of API-Model (Resource) to the internal data model.
     *
     * @return the converted instance
     */
    public Shipment toShipment() {
        Shipment toConvert = new Shipment();
        toConvert.trackingId = trackingId;
        toConvert.customer = customer;
        toConvert.senderAddress = senderAddress;
        toConvert.receiverAddress = receiverAddress;

        return toConvert;
    }
}
