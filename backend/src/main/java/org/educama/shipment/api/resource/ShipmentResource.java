package org.educama.shipment.api.resource;

import org.educama.enums.ClientType;
import org.educama.enums.Status;
import org.educama.shipment.api.datastructure.CargoDS;
import org.educama.shipment.api.datastructure.FlightDS;
import org.educama.shipment.api.datastructure.PartyDS;
import org.educama.shipment.api.datastructure.ServicesDS;
import org.educama.shipment.model.Flight;
import org.educama.shipment.model.Shipment;

/**
 * REST-Resource for single shipment.
 */
public class ShipmentResource {

    public String trackingId;
    public PartyDS sender;
    public PartyDS receiver;
    public CargoDS shipmentCargo;
    public ServicesDS shipmentServices;
    public ClientType customerTypeEnum;
    public FlightDS shipmentFlight;
    public Status status;

    /**
     * Create a API-Model (Resource) instance from the internal data model.
     *
     * @param shipmentModel instance of the internal-data model
     * @return a converted ShipmentResource
     */
    public ShipmentResource fromShipment(Shipment shipmentModel) {
        this.trackingId = shipmentModel.trackingId;
        this.sender = new PartyDS(shipmentModel.sender);
        this.receiver = new PartyDS(shipmentModel.receiver);
        this.shipmentCargo = new CargoDS(shipmentModel.shipmentCargo);
        this.shipmentServices = new ServicesDS(shipmentModel.shipmentServices);
        this.customerTypeEnum = shipmentModel.customerTypeEnum;
        this.shipmentFlight = new FlightDS(shipmentModel.shipmentFlight == null ? new Flight() : shipmentModel.shipmentFlight);
        this.status = shipmentModel.statusEnum;

        return this;
    }
}
