package org.educama.shipment.boundary.impl;

import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.model.Shipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShipmentBoundaryServiceImpl implements ShipmentBoundaryService {

    @Override
    public Shipment createShipment(Shipment shipment) {

        // TODO call repository to create shipment entity

        // TODO call camunda api to create a case instance

        // do a mock, temp
        Shipment s = new Shipment();
        s.senderAdress = "TADA";
        s.receiverAdress = "AHA";

        return s;
    }

    @Override
    public Collection<Shipment> findAll() {

        // TODO call camunda api to get all instances

        List<Shipment> allShipments = new ArrayList<>();

        // do a mock, temp
        Shipment s = new Shipment();
        s.senderAdress = "TADA";
        s.receiverAdress = "AHA";
        for(int i=0; i < 5; i++) {

            allShipments.add(s);
        }

        return allShipments;
    }
}