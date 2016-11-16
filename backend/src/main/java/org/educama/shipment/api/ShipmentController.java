package org.educama.shipment.api;

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

@RestController
@RequestMapping(path = ShipmentController.SHIPMENT_RESOURCE_PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ShipmentController {

    public static final String SHIPMENT_RESOURCE_PATH = "/v1/shipments";

    @Autowired
    private ShipmentBoundaryService shipmentBoundaryService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createShipment(@RequestBody ShipmentResource shipmentWsResourceToCreate) {

        Shipment convertedShipment = shipmentWsResourceToCreate.toShipment();
        Shipment createdShipment = shipmentBoundaryService.createShipment(convertedShipment);

        ShipmentResource responseShipmentResource = new ShipmentResource(createdShipment);

        return new ResponseEntity<>(responseShipmentResource, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ShipmentListResource findAllShipments() {

        Collection<Shipment> allShipments = shipmentBoundaryService.findAll();
        ShipmentListResource resourceList = new ShipmentListResource(allShipments);

        return resourceList;
    }
}