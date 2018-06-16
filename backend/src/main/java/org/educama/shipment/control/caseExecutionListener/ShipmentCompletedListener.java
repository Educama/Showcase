package org.educama.shipment.control.caseExecutionListener;

import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.educama.common.api.BeanUtil;
import org.educama.enums.Status;
import org.educama.shipment.boundary.ShipmentBoundaryService;

import java.util.logging.Logger;

/**
 * Listener to get event "Shipment case instance completed" from "Shipment Case".
 */

public class ShipmentCompletedListener implements CaseExecutionListener {

    private ShipmentBoundaryService shipmentBoundaryService = BeanUtil.getBean(ShipmentBoundaryService.class);
    private static final Logger LOGGER = Logger.getLogger("LOAN-REQUESTS-CMMN");

    public void notify(DelegateCaseExecution caseExecution) throws Exception {
        LOGGER.info("Plan Item '" + caseExecution.getActivityId() + "' labeled '"
               + caseExecution.getActivityName() + "' has performed transition: "
                + caseExecution.getEventName() + "   " + caseExecution.getCaseBusinessKey());

        shipmentBoundaryService.setStatus(caseExecution.getCaseBusinessKey(), Status.SHIPMENT_COMPLETED);

    }
}
