package org.educama.shipment.process.sentries;

import org.camunda.bpm.engine.CaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class evaluates the sentries in the case diagram.
 */
@Component
public class FlightDepartedSentry {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightDepartedSentry.class);
    private CaseService caseService;

    @Autowired
    public FlightDepartedSentry(CaseService caseService) {
        this.caseService = caseService;
    }

    public boolean isDeparted(String executionId) {
        if (caseService.getVariable(executionId, "isDeparted") == null) {
            this.setDeparted(executionId, false);
        }
        return (boolean) caseService.getVariable(executionId, "isDeparted");
    }

    public void setDeparted(String executionId, boolean departed) {
        caseService.setVariable(executionId, "isDeparted", departed);
    }
}
