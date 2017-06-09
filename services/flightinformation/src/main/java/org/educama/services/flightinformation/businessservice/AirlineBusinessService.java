package org.educama.services.flightinformation.businessservice;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.educama.services.flightinformation.datafeed.AirlineCsvDeserializer;
import org.educama.services.flightinformation.model.Airline;
import org.educama.services.flightinformation.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AirlineBusinessService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private AirlineCsvDeserializer airlineCsvDeserializer;

	protected static int maxSuggestions = 10;

	public List<Airline> findAllAirlines() {
		return airlineRepository.findAll();
	}

	public List<Airline> findAirlinesByIataCode(String iataCode) {
		return airlineRepository.findByIataCode(iataCode.toUpperCase());
	}

	public List<Airline> findAirlinesSuggestionsByIataCode(String iataCode) {
		if (StringUtils.isEmpty(iataCode)) {
			return Collections.emptyList();
		}

		List<Airline> suggestions = airlineRepository.findByIataCodeLike(iataCode.toUpperCase());

		return suggestions.stream()
		    .limit(maxSuggestions)
		    .collect(Collectors.toList());
	}

	public List<Airline> findAirlinesSuggestionsByCallSign(String callSign) {
		if (StringUtils.isEmpty(callSign)) {
			return Collections.emptyList();
		}

		List<Airline> suggestions = airlineRepository.findByCallSignLike(callSign.toUpperCase());

		return suggestions.stream()
		    .limit(maxSuggestions)
		    .collect(Collectors.toList());
	}

	public void clearAndImportAirlines(MultipartFile file) throws IOException {
		List<Airline> airlines = airlineCsvDeserializer.deserialize(file.getInputStream());

		airlineRepository.deleteAll();
		airlineRepository.save(airlines);

	}

}
