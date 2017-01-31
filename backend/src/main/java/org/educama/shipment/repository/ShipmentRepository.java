package org.educama.shipment.repository;

import org.educama.shipment.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

   Shipment findOneBytrackingId(String trackingId);
}
