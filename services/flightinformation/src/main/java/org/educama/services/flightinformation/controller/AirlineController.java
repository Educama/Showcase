package org.educama.services.flightinformation.controller;

import java.io.IOException;
import java.util.List;

import org.educama.services.flightinformation.businessservice.AirlineBusinessService;
import org.educama.services.flightinformation.model.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AirlineController {

	@Autowired
	AirlineBusinessService airlineBusinessService;

	/**
	 * Retrieves all airlines
	 *
	 * @return
	 */
	@RequestMapping("/airlines")
	public List<Airline> getAirlines() {
		return airlineBusinessService.findAllAirlines();
	}

	/**
	 * Retrieves an airline by its IATA code
	 *
	 * @param iataCode
	 *            the IATA Code
	 * @return the airline.
	 */
	@RequestMapping("/airlines/{iataCode}")
	public List<Airline> getAirlinesByIataCode(@PathVariable String iataCode) {
		return airlineBusinessService.findAirlinesByIataCode(iataCode.toUpperCase());
	}

	/**
	 * Retrieves a list of airlines which IATA code begin with a given term.
	 *
	 * @param iataCode
	 *            the part of the IATA code to be looked up.
	 * @return the list of matching airlines.
	 */
	@RequestMapping("/airlines/suggestions")
	public List<Airline> getAirlinesSuggestionsByIataCode(@RequestParam(value = "term") String iataCode) {
		return airlineBusinessService.findAirlinesSuggestionsByIataCode(iataCode);

	}

	/**
	 * Retrieves a list of airlines which call ssign contain the given term
	 *
	 * @param callSign
	 *            the part of the call sign to be looked up
	 * @return the list aof matching airlines.
	 */
	@RequestMapping("/airlines/suggestions/callsign")
	public List<Airline> getAirlinesSuggestionsByCallSign(@RequestParam(value = "term") String callSign) {
		return airlineBusinessService.findAirlinesSuggestionsByCallSign(callSign);
	}

	/**
	 * Replaces the content of the airlines database with the content of the CSV file the data contained in the csv
	 * File.
	 *
	 * @param file
	 *            the import file containing the airline data
	 */
	@RequestMapping(value = "/airlines/import/csv", method = RequestMethod.POST)
	public @ResponseBody void importAirlines(@RequestParam("file") MultipartFile file) throws IOException {
		airlineBusinessService.clearAndImportAirlines(file);
	}
}
