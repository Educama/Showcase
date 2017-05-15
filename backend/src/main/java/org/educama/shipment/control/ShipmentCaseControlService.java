package org.educama.shipment.control;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Control service for shipments.
 */
@Service
public class ShipmentCaseControlService {

    private static final String SHIPMENTCASEKEY = "CaseId_shipment";

    @Autowired
    private CaseService caseService;

    /**
     * Creates a new CaseInstance with a generated business key.
     *
     * @return the new CaseInstance
     */
    public CaseInstance create() {
        String businessKey = UUID.randomUUID().toString();
        CaseInstance caseInstance = caseService.createCaseInstanceByKey(SHIPMENTCASEKEY, businessKey);

        return caseInstance;
    }
}
