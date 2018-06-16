package org.educama.shipment.boundary;

import org.educama.enums.Status;
import org.educama.shipment.api.resource.InvoiceResource;
import org.educama.shipment.api.resource.ShipmentResource;
import org.educama.shipment.model.Invoice;
import org.educama.shipment.model.Flight;
import org.educama.shipment.model.Shipment;

import java.util.Collection;

/**
 * Boundary service for shipment.
 */
public interface ShipmentBoundaryService {

    /**
     * Creates a shipment.
     *
     * @param shipment which is to be created as Case
     * @return the created shipment
     */
    Shipment createShipment(Shipment shipment);

    /**
     * Retrieves all shipments.
     *
     * @return a collection of all shipments
     */
    Collection<Shipment> findAll();

    /**
     * Retrieves one shipment.
     *
     * @param trackingId to get required shipment
     * @return returns the shipment as a resource
     */
    ShipmentResource getShipment(String trackingId);

    /**
     * Retrieves one invoice.
     *
     * @param trackingId to get required shipment
     * @param saveInvoiceResource to get invoice data
     * @return returns the shipment as a resource
     */
    InvoiceResource createInvoice(String trackingId, Invoice saveInvoiceResource);

    /**
     * Retrieves all matching invoices for a shipment.
     *
     * @param trackingId to get required shipment
     * @return returns a collection of matching Invoices
     */
    Collection<Invoice> getInvoices(String trackingId);

    /**
     * Retrieves one shipment and updates it.
     *
     * @param trackingId to get required shipment
     * @return returns the  updated shipment as a resource
     */
    ShipmentResource updateShipment(String trackingId, Shipment saveShipmentResource);

    /**
     * Sets the status of a shipment.
     *
     * @param trackingId to get required shipment
     * @param status the new status of the shipment
     */
    void setStatus(String trackingId, Status status);

    /**
     * Retrieves one shipment and adds shipmentFlight Information.
     *
     * @param trackingId to get required shipment
     * @return returns the  updated shipment as a resource
     */
    ShipmentResource addFlightToShipment(String trackingId, Flight saveFlightResource);

    /**
     * @param trackingId to get required shipment
     * @return returns true if the Flight Departed Milestone is completed
     */
    boolean completeFlightDeparted(String trackingId);
}
