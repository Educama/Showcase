package org.educama.shipment.api;

import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Customer;
import org.educama.shipment.api.resource.SaveShipmentResource;
import org.educama.shipment.api.resource.ShipmentListResource;
import org.educama.shipment.api.resource.ShipmentResource;
import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import javax.validation.Valid;

/**
 * REST-Service to access Shipment resources.
 */
@RestController
@RequestMapping(path = ShipmentController.SHIPMENT_RESOURCE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ShipmentController {

    public static final String SHIPMENT_RESOURCE_PATH = "/educama/v1/shipments";

    @Autowired
    private ShipmentBoundaryService shipmentBoundaryService;

    @Autowired
    private CustomerBoundaryService customerService;

    /**
     * API call to create a shipment.
     *
     * @param shipmentWsResourceToCreate as instance of the API-Model (Resource)
     * @return the created instance converted back into the API-Model (Resource)
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createShipment(@RequestBody SaveShipmentResource shipmentWsResourceToCreate) {
        Customer sender = customerService.findCustomerByUuid(shipmentWsResourceToCreate.uuidSender);
        Customer receiver = customerService.findCustomerByUuid(shipmentWsResourceToCreate.uuidReceiver);
        Shipment convertedShipment = shipmentWsResourceToCreate.toShipment();
        convertedShipment.sender = sender;
        convertedShipment.receiver = receiver;
        Shipment createdShipment = shipmentBoundaryService.createShipment(convertedShipment);
        ShipmentResource responseShipmentResource = new ShipmentResource().fromShipment(createdShipment);

        return new ResponseEntity<>(responseShipmentResource, HttpStatus.CREATED);
    }

    /**
     * API call to select all shipments.
     *
     * @return the result collection converted into the API-Model (Resource)
     */
    @RequestMapping(method = RequestMethod.GET)
    public ShipmentListResource shipments() {
        Collection<Shipment> allShipments = shipmentBoundaryService.findAll();
        ShipmentListResource resourceList = new ShipmentListResource().fromShipmentCollection(allShipments);

        return resourceList;
    }

    /**
     * API call to select one shipment.
     *
     * @return one shipment converted into the API-Model (Resource)
     */
    @RequestMapping(value = "/{trackingId}", method = RequestMethod.GET)
    public ShipmentResource getShipment(@PathVariable("trackingId") String trackingId) {
        ShipmentResource shipment = shipmentBoundaryService.getShipment(trackingId);
        return shipment;
    }

    /**
     * API call to update one shipment.
     *
     * @returns the updated shipment converted into the API-Model (Resource)
     */
    @RequestMapping(value = "/{trackingId}", method = RequestMethod.PUT)
    public ShipmentResource updateShipment(@PathVariable("trackingId") String trackingId,
                                           @Valid @RequestBody SaveShipmentResource saveShipmentResource) {
        Shipment shipment = saveShipmentResource.toShipment();
        Customer sender = customerService.findCustomerByUuid(saveShipmentResource.uuidSender);
        Customer receiver = customerService.findCustomerByUuid(saveShipmentResource.uuidReceiver);
        shipment.sender = sender;
        shipment.receiver = receiver;
        ShipmentResource convertedShipment = shipmentBoundaryService.updateShipment(trackingId, shipment);
        return convertedShipment;
    }
}
