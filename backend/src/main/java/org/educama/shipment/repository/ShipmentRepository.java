package org.educama.shipment.repository;

import org.educama.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for accessing shipment entities.
 */
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

   Shipment findOneBytrackingId(String trackingId);
}
