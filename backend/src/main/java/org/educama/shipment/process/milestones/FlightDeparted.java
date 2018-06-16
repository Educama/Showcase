package org.educama.shipment.process.milestones;

import org.camunda.bpm.engine.ProcessEngine;
import org.educama.shipment.process.ShipmentCaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class representing an interface to the Flight departed Milestone.
 */
@Component
public class FlightDeparted {

    private ProcessEngine processEngine;

    @Autowired
    public FlightDeparted(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public boolean isAvailable(String trackingId) {
        return processEngine.getCaseService().createCaseExecutionQuery()
                .activityId(ShipmentCaseConstants.PLAN_ITEM_MILESTONE_FLIGHT_DEPARTED)
                .caseInstanceBusinessKey(trackingId)
                .available()
                .singleResult() != null;
    }
}
