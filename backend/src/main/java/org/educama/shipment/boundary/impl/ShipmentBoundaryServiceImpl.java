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

package org.educama.shipment.boundary.impl;

import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.model.Shipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ShipmentBoundaryServiceImpl implements ShipmentBoundaryService {

    @Override
    public Shipment createShipment(Shipment shipment) {
        // call repository to create shipment entity

        // call camunda api to create a case instance
        return null;
    }
}
