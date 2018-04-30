package org.educama.shipment.boundary.impl;

import org.camunda.bpm.engine.ProcessEngine;
import org.educama.common.exceptions.ResourceNotFoundException;
import org.educama.shipment.api.resource.ShipmentResource;
import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.educama.shipment.control.ShipmentCaseControlService;
import org.educama.shipment.model.Flight;
import org.educama.shipment.model.Shipment;
import org.educama.shipment.process.ShipmentCaseEvaluator;
import org.educama.shipment.process.tasks.CompleteShipmentOrderTask;
import org.educama.shipment.process.tasks.OrganizeFlightTask;
import org.educama.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

/**
 * Boundary service implementation for shipments.
 */
@Service
@Transactional(readOnly = false)
public class ShipmentBoundaryServiceImpl implements ShipmentBoundaryService {

    private CompleteShipmentOrderTask completeShipmentOrderTask;

    private OrganizeFlightTask organizeFlightTask;

    private ShipmentRepository shipmentRepository;

    private ShipmentCaseControlService shipmentCaseControlService;

    private ShipmentCaseEvaluator shipmentCaseEvaluator;

    @Autowired
    public ShipmentBoundaryServiceImpl(CompleteShipmentOrderTask completeShipmentOrderTask,
                                       OrganizeFlightTask organizeFlightTask,
                                       ShipmentRepository shipmentRepository,
                                       ShipmentCaseControlService shipmentCaseControlService,
                                       ShipmentCaseEvaluator shipmentCaseEvaluator,
                                       ProcessEngine processEngine) {
        this.completeShipmentOrderTask = completeShipmentOrderTask;
        this.organizeFlightTask = organizeFlightTask;
        this.shipmentRepository = shipmentRepository;
        this.shipmentCaseControlService = shipmentCaseControlService;
        this.shipmentCaseEvaluator = shipmentCaseEvaluator;
    }

    @Override
    @Transactional(readOnly = false)
    public Shipment createShipment(Shipment shipment) {
        shipment.trackingId = UUID.randomUUID().toString();
        Shipment createdShipment = shipmentRepository.saveAndFlush(shipment);
        shipmentCaseControlService.create(shipment.trackingId);
        return createdShipment;
    }

    @Override
    public Collection<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    @Override
    public ShipmentResource getShipment(String trackingId) {
        Shipment shipment = shipmentRepository.findOneBytrackingId(trackingId);
        ShipmentResource convertedShipment = new ShipmentResource().fromShipment(shipment);
        return convertedShipment;
    }

    @Override
    @Transactional(readOnly = false)
    public ShipmentResource updateShipment(String trackingId, Shipment saveShipmentResource) {
        Shipment shipment = shipmentRepository.findOneBytrackingId(trackingId);
        if (shipment == null) {
            throw new ResourceNotFoundException("Shipment not found");
        } else {
            shipment.customerTypeEnum = saveShipmentResource.customerTypeEnum;
            shipment.receiver = saveShipmentResource.receiver;
            shipment.sender = saveShipmentResource.sender;
            shipment.shipmentCargo = saveShipmentResource.shipmentCargo;
            shipment.shipmentServices = saveShipmentResource.shipmentServices;
            shipment = shipmentRepository.saveAndFlush(shipment);

            if (completeShipmentOrderTask.isActive(trackingId) && completeShipmentOrderTask.canBeCompleted(trackingId)) {
                completeShipmentOrderTask.complete(trackingId);
                shipmentCaseEvaluator.reevaluateCase(trackingId);
            }

            ShipmentResource convertedShipment = new ShipmentResource().fromShipment(shipment);
            return convertedShipment;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public ShipmentResource addFlightToShipment(String trackingId, Flight saveFlightResource) {
        Shipment shipment = shipmentRepository.findOneBytrackingId(trackingId);
        if (shipment == null) {
            throw new ResourceNotFoundException("Shipment not found");
        } else {
            shipment.shipmentFlight = saveFlightResource;
            shipment = shipmentRepository.saveAndFlush(shipment);
            organizeFlightTask.complete(trackingId);
            shipmentCaseEvaluator.reevaluateCase(trackingId);
            ShipmentResource convertedShipment = new ShipmentResource().fromShipment(shipment);
            return convertedShipment;
        }
    }

}
