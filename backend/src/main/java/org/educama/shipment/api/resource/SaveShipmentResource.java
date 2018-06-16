package org.educama.shipment.api.resource;

import java.util.UUID;


import org.educama.enums.ClientType;
import org.educama.enums.Status;
import org.educama.shipment.api.datastructure.CargoDS;
import org.educama.shipment.api.datastructure.FlightDS;
import org.educama.shipment.api.datastructure.ServicesDS;
import org.educama.shipment.model.Flight;
import org.educama.shipment.model.Shipment;

/**
 * REST-Resource for create or put Methods for shipments.
 */
public class SaveShipmentResource {

    public String trackingId;
    public UUID uuidSender;
    public UUID uuidReceiver;
    public CargoDS shipmentCargo;
    public ServicesDS shipmentServices;
    public ClientType customerTypeEnum;
    public FlightDS shipmentFlight;
    public Status statusEnum;

    /**
     * Convert this instance of API-Model (Resource) to the internal data model.
     *
     * @return the converted instance
     */
    public Shipment toShipment() {
        Shipment toConvert = new Shipment();
        toConvert.trackingId = trackingId;
        toConvert.shipmentCargo = shipmentCargo.toCargo();
        toConvert.shipmentServices = shipmentServices.toServices();
        toConvert.customerTypeEnum = customerTypeEnum;
        toConvert.shipmentFlight = (shipmentFlight == null ? new Flight() : shipmentFlight.toFlight());
        toConvert.statusEnum = statusEnum;
        return toConvert;
    }
}
