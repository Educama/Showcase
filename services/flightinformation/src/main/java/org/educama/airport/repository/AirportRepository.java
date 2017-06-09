package org.educama.airport.repository;

import java.util.List;

import org.educama.airport.model.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {

    public Airport findByName(String name);

    public List<Airport> findByIataCode(String iataCode);

    public List<Airport> findByNameLike(String name);

    public List<Airport> findByIataCodeLike(String iataCode);

    public List<Airport> findByIcaoCode(String icaoCode);

}
