package org.educama.services.flightinformation.datafeed;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.educama.services.flightinformation.model.Airline;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AirlineCsvDeserializerIntTest {

	private final String SEPARATOR = ",";

	@Autowired
	private AirlineCsvDeserializer cut = new AirlineCsvDeserializer();

	private static final String EMPTY = "";

	@Test
	public void deserialize_createsNewAirlinesInstances_WhenValidCsvInput() throws IOException {
		// Given
		final String name1 = "Air Canada";
		final String alias1 = "";
		final String iata1 = "AC";
		final String icao1 = "ACA";
		final String callsign1 = "AIR CANADA";
		final String country1 = "Canada";

		final String name2 = "Aeroflot Russian Airlines";
		final String alias2 = "";
		final String iata2 = "SU";
		final String icao2 = "AFL";
		final String callsign2 = "AEROFLOT";
		final String country2 = "Russia";

		Airline firstAirline = new Airline().withName(name1)
		    .withAlias(alias1)
		    .withIataCode(iata1)
		    .withIataCode(icao1)
		    .withCallSign(callsign1)
		    .withCountry(country1);

		Airline secondAirline = new Airline().withName(name2)
		    .withAlias(alias2)
		    .withIataCode(iata2)
		    .withIataCode(icao2)
		    .withCallSign(callsign2)
		    .withCountry(country2);

		List<Airline> airlines = new ArrayList<>();
		airlines.add(firstAirline);
		airlines.add(secondAirline);
		String csvContent = this.createValidCsvContentForAirlines(airlines);

		InputStream inputStream = new ByteArrayInputStream(
		    csvContent.getBytes(StandardCharsets.UTF_8));

		// When
		List<Airline> actualAirlines = cut.deserialize(inputStream);

		// Then
		assertThat(actualAirlines.size()).isEqualTo(2);
		assertThat(actualAirlines.contains(firstAirline));
		assertThat(actualAirlines.contains(secondAirline));
	}

	private String createValidCsvContentForAirlines(List<Airline> airlines) {
		StringBuilder builder = new StringBuilder();
		for (Airline airline : airlines) {
			// @formatter:off
			builder.append(EMPTY)
			    .append(SEPARATOR)  // ID
			    .append(airline.getName())
			    .append(SEPARATOR)
			    .append(airline.getAlias())
			    .append(SEPARATOR)
			    .append(airline.getIataCode())
			    .append(SEPARATOR)
			    .append(airline.getIcaoCode())
			    .append(SEPARATOR)
			    .append(airline.getCallSign())
			    .append(SEPARATOR)
			    .append(airline.getCountry())
			    .append(SEPARATOR)
			    .append(EMPTY)
			    .append("\n"); // Active
			// @formatter:on
		}
		return builder.toString();
	}
}
