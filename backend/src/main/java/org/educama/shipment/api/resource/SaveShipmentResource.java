package org.educama.shipment.api.resource;

import java.util.UUID;

import org.educama.customer.model.Customer;
import org.educama.enums.ClientType;
import org.educama.shipment.model.Cargo;
import org.educama.shipment.model.Services;
import org.educama.shipment.model.Shipment;

/**
 * REST-Resource for create or put Methods for shipments.
 */
public class SaveShipmentResource {

    public String trackingId;
    public UUID uuidSender;
    public UUID uuidReceiver;
    public Customer sender;
    public Customer receiver;
    public Cargo shipmentCargo;
    public Services shipmentServices;
    public ClientType customerTypeEnum;

    /**
     * Convert this instance of API-Model (Resource) to the internal data model.
     *
     * @return the converted instance
     */
    public Shipment toShipment() {
        Shipment toConvert = new Shipment();
        toConvert.trackingId = trackingId;
        toConvert.sender = sender;
        toConvert.receiver = receiver;
        toConvert.shipmentCargo = shipmentCargo;
        toConvert.shipmentServices = shipmentServices;
        toConvert.customerTypeEnum = customerTypeEnum;

        return toConvert;
    }
}
