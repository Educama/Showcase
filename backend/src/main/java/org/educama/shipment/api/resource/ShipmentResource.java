package org.educama.shipment.api.resource;

import java.util.UUID;

import org.educama.customer.model.Customer;
import org.educama.shipment.model.Shipment;

/**
 * REST-Resource for single shipment.
 */
public class ShipmentResource {

    public String trackingId;
    public UUID uuidSender;
    public UUID uuidReceiver;
    public Customer sender;
    public Customer receiver;
    public boolean senderIsEmployer;
    public boolean receiverIsEmployer;
    public int numberPackages;
    public double totalWeight;
    public double totalCapacity;
    public String cargoDescription;
    public boolean dangerousGoods;
    public boolean forerun;
    public boolean exportInsurance;
    public boolean exportCustomsClearance;
    public boolean flight;
    public boolean importInsurance;
    public boolean importCustomsClearance;
    public boolean backlash;

    /**
     * Create a API-Model (Resource) instance from the internal data model.
     *
     * @param shipmentModel instance of the internal-data model
     * @return a converted ShipmentResource
     */
    public ShipmentResource fromShipment(Shipment shipmentModel) {
        this.trackingId = shipmentModel.trackingId;
        this.uuidSender = shipmentModel.uuidSender;
        this.uuidReceiver = shipmentModel.uuidReceiver;
        this.sender = shipmentModel.sender;
        this.receiver = shipmentModel.receiver;
        this.senderIsEmployer = shipmentModel.senderIsEmployer;
        this.receiverIsEmployer = shipmentModel.receiverIsEmployer;
        this.numberPackages = shipmentModel.numberPackages;
        this.totalWeight = shipmentModel.totalWeight;
        this.totalCapacity = shipmentModel.totalCapacity;
        this.cargoDescription = shipmentModel.cargoDescription;
        this.dangerousGoods = shipmentModel.dangerousGoods;
        this.forerun = shipmentModel.forerun;
        this.exportInsurance = shipmentModel.exportInsurance;
        this.exportCustomsClearance = shipmentModel.exportCustomsClearance;
        this.flight = shipmentModel.flight;
        this.importInsurance = shipmentModel.importInsurance;
        this.importCustomsClearance = shipmentModel.importCustomsClearance;
        this.backlash = shipmentModel.backlash;


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
        toConvert.uuidSender = uuidSender;
        toConvert.uuidReceiver = uuidReceiver;
        toConvert.sender = sender;
        toConvert.receiver = receiver;
        toConvert.senderIsEmployer = senderIsEmployer;
        toConvert.receiverIsEmployer = receiverIsEmployer;
        toConvert.numberPackages = numberPackages;
        toConvert.totalWeight = totalWeight;
        toConvert.totalCapacity = totalCapacity;
        toConvert.cargoDescription = cargoDescription;
        toConvert.dangerousGoods = dangerousGoods;
        toConvert.forerun = forerun;
        toConvert.exportInsurance = exportInsurance;
        toConvert.exportCustomsClearance = exportCustomsClearance;
        toConvert.flight = flight;
        toConvert.importInsurance = importInsurance;
        toConvert.importCustomsClearance = importCustomsClearance;
        toConvert.backlash = backlash;

        return toConvert;
    }
}
