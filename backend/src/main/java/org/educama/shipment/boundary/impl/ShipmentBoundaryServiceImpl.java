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

	/**
	 * To ensure a technically working API creating a Shipment is currently done by returning a simple test-instance.
	 * It is planned to store the given instance into a Spring-Repository and sending it to the CMN-engine.
	 */
    @Override
    public Shipment createShipment(Shipment shipment) {
        // TODO call repository to store shipment entity
        // TODO call camunda api to create a case instance

        // create a mock-object
    	Shipment createdShipment = createEnhancedShipmentTestInstance(shipment, "test");

        return createdShipment;
    }

    /**
     * To ensurce a technically working API looking up all Shipments is be done by creating a collection of test objects.
     */
    @Override
    public Collection<Shipment> findAll() {
        // TODO call camunda api to get all instances
        List<Shipment> allShipments = new ArrayList<>();

        // create a mock-object
        for(int i=0; i < 5; i++) {
        	Shipment currentCreatedShipment = createShipmentTestInstance("collection-test-" + i);
            allShipments.add(currentCreatedShipment);
        }

        return allShipments;
    }
    
    /**
     * Create a Test-Instance for mocking a technically working API.
     * 
     * @param suffix to the Shipment-properties
     * @return created test-instance
     */
    private Shipment createShipmentTestInstance(String suffix) {
    	Shipment createdShipment = new Shipment();
        createdShipment.senderAddress = "senderAdress - " + suffix;
        createdShipment.receiverAddress = "receiverAdress - " + suffix;
        
        return createdShipment;
    }
    
    /**
     * Most likely {@link #createShipmentTestInstance(String)} but enhanced by the origins-request-instance informations.
     * 
     * @param originShipment instance which informations will be put before the suffix-parameter
     * @param suffix to the Shipment-properties
     * @return created test-instance
     */
    private Shipment createEnhancedShipmentTestInstance(Shipment originShipment, String suffix) {
    	Shipment createdShipment = new Shipment();
        createdShipment.senderAddress = "originShipmentSenderAdress: " + originShipment.senderAddress + " - " + suffix;
        createdShipment.receiverAddress = "originShipmentReceiverAdress:" + originShipment.receiverAddress + " - " + suffix;
        
        return createdShipment;
    }
}
