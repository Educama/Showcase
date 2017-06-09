package org.educama.services.flightinformation.controller;

import java.io.IOException;
import java.util.List;

import org.educama.services.flightinformation.businessservice.AirportBusinessService;
import org.educama.services.flightinformation.model.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AirportController {

	@Autowired
	private AirportBusinessService airportBusinessService;

	/**
	 * Retrieves all airports.
	 * 
	 * @return the airports
	 */
	@RequestMapping("/airports")
	public List<Airport> getAirports() {
		return airportBusinessService.findAllAirports();
	}

	/**
	 * Retrieves an airport by its IATA code
	 *
	 * @param iataCode
	 *            the IATA Code
	 * @return the airport.
	 */
	@RequestMapping("/airports/{iataCode}")
	public List<Airport> getAirportByIataCode(@PathVariable String iataCode) {
		return airportBusinessService.findAirportByIataCode(iataCode.toUpperCase());
	}

	/**
	 * Retrieves the a list of airports which IATA code begin with a given term.
	 *
	 * @param iataCode
	 *            the part of the IATA code to be looked up.
	 * @return the list of matching airports.
	 */
	@RequestMapping("/airports/suggestions")
	public List<Airport> getAirportSuggestions(@RequestParam(value = "term") String iataCode) {
		List<Airport> suggestions = airportBusinessService.findAirportsSuggestionsByIataCode(iataCode);

		return suggestions;
	}

	/**
	 * Replaces the content of the airports database with the content of the CSV file the data contained in the csv
	 * File.
	 *
	 * @param file
	 *            the import file containing the airport data
	 */
	@RequestMapping(value = "/airports/import/csv", method = RequestMethod.POST)
	public @ResponseBody void importAirports(@RequestParam("file") MultipartFile file) throws IOException {
		airportBusinessService.clearAndImportAirports(file);
	}
}
