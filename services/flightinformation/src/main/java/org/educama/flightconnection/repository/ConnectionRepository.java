package org.educama.flightconnection.repository;

import org.educama.flightconnection.model.Connection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface ConnectionRepository extends MongoRepository<Connection, String> {
    public Page<Connection> findBySourceAirportIataCodeAndDestinationAirportIataCode(String sourceIataCode, String destinationAirportIataCode, Pageable pageable);

    public Page<Connection> findBySourceAirportIataCode(String sourceIataCode, Pageable pageable);

    public Page<Connection> findBydestinationAirportIataCode(String destinationIataCode, Pageable pageable);
}
