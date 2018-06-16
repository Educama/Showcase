package org.educama.enums;

import java.io.Serializable;

/**
 * The status of the shipment.
 */
public enum Status implements Serializable {
    SHIPMENT_ORDER_INCOMPLETE, SHIPMENT_ORDER_COMPLETED, FLIGHT_DEPARTED, SHIPMENT_COMPLETED
}
