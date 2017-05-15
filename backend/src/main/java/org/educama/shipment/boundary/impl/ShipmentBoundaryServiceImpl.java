package org.educama.shipment.boundary.impl;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.control.ShipmentCaseControlService;
import org.educama.shipment.model.Shipment;
import org.educama.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Boundary service implementation for shipments.
 */
@Service
@Transactional(readOnly = true)
public class ShipmentBoundaryServiceImpl implements ShipmentBoundaryService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentCaseControlService shipmentCaseControlService;

    @Override
    public Shipment createShipment(Shipment shipment) {
        CaseInstance caseInstance = shipmentCaseControlService.create();
        shipment.trackingId = caseInstance.getBusinessKey();
        Shipment createdShipment = shipmentRepository.save(shipment);
        return createdShipment;
    }

    @Override
    public Collection<Shipment> findAll() {
        return shipmentRepository.findAll();
    }
}
