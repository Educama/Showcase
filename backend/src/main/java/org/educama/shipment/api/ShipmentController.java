/*
 * *************************************************************************
 *
 * Copyright:       Robert Bosch GmbH, 2016
 *
 * *************************************************************************
 */

/**
 * Dear "developer", please document your class RIGHT HERE!.
 */

package org.educama.shipment.api;

import org.educama.shipment.api.resource.ShipmentListResource;
import org.educama.shipment.api.resource.ShipmentResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ShipmentController.SHIPMENT_RESOURCE_PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ShipmentController {

    public static final String SHIPMENT_RESOURCE_PATH = "/v1/shipments";

    // URL .../shipments und Method POST
    public ResponseEntity<?> createShipment(@RequestBody ShipmentResource shipment) {
        // call boundary service
        HttpHeaders httpHeaders = new HttpHeaders();
        // we need to use the header later as well
        ShipmentListResource resource = new ShipmentListResource();
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.CREATED);
    }

    // URL .../shipments und Method GET
    public ShipmentListResource findAllShipments() {
        // call boundary service
        return new ShipmentListResource();
    }
}
