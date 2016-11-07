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

package org.educama.shipment.repository;

import org.educama.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
