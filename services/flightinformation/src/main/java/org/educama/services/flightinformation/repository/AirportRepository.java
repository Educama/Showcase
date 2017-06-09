package org.educama.services.flightinformation.repository;

import java.util.List;

import org.educama.services.flightinformation.model.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirportRepository extends MongoRepository<Airport, String> {

	public Airport findByName(String name);

	public List<Airport> findByIataCode(String iataCode);

	public List<Airport> findByNameLike(String name);

	public List<Airport> findByIataCodeLike(String iataCode);

}
