package org.educama.shipment.api;

import org.educama.customer.boundary.CustomerBoundaryService;
import org.educama.customer.model.Customer;
import org.educama.shipment.api.resource.ShipmentListResource;
import org.educama.shipment.api.resource.ShipmentResource;
import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
    public ResponseEntity<?> createShipment(@RequestBody ShipmentResource shipmentWsResourceToCreate) {
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
}
