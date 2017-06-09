package org.educama.services.flightinformation.businessservice;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.educama.services.flightinformation.datafeed.AirportCsvDeserializer;
import org.educama.services.flightinformation.model.Airport;
import org.educama.services.flightinformation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AirportBusinessService {

	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private AirportCsvDeserializer airportCsvDeserializer;

	protected static int maxSuggestions = 10;

	public List<Airport> findAllAirports() {
		return airportRepository.findAll();
	}

	public List<Airport> findAirportByIataCode(String iataCode) {
		return airportRepository.findByIataCode(iataCode.toUpperCase());
	}

	public List<Airport> findAirportsSuggestionsByIataCode(String iataCode) {
		if (StringUtils.isEmpty(iataCode)) {
			return Collections.emptyList();
		}

		List<Airport> suggestions = airportRepository.findByIataCodeLike(iataCode.toUpperCase());

		return suggestions.stream().limit(maxSuggestions).collect(Collectors.toList());
	}

	public void clearAndImportAirports(MultipartFile file) throws IOException {
		List<Airport> airports = airportCsvDeserializer.deserialize(file.getInputStream());

		airportRepository.deleteAll();
		airportRepository.save(airports);

	}

}
