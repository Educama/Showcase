package org.educama.services.flightinformation.datafeed;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.educama.services.flightinformation.model.Airport;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AirportCsvDeserializerIntTest {

	private final String SEPARATOR = ",";

	@Autowired
	private AirportCsvDeserializer cut = new AirportCsvDeserializer();

	private static final String EMPTY = "";

	@Test
	public void deserialize_createsNewAirportsInstances_WhenValidCsvInput() throws IOException {
		// Given
		final String name1 = "Los Angeles International Airport";
		final String city1 = "Los Angeles";
		final String country1 = "USA";
		final String iata1 = "LAX";
		final String icao1 = "KLAX";
		final double latitude1 = 33.94250107;
		final double longitude1 = -118.4079971;

		final String name2 = "Frankfurt am Main International Airport";
		final String city2 = "Frankfurt";
		final String country2 = "Germany";
		final String iata2 = "FRA";
		final String icao2 = "EDDF";
		final double latitude2 = 50.0333333;
		final double longitude2 = 8.5705556;

		Airport firstAirport = new Airport().withName(name1)
		    .withCity(city1)
		    .withCountry(country1)
		    .withIataCode(iata1)
		    .withIcaoCode(icao1)
		    .withLatitude(latitude1)
		    .withLongitude(longitude1);

		Airport secondAirport = new Airport().withName(name2)
		    .withCity(city2)
		    .withCountry(country2)
		    .withIataCode(iata2)
		    .withIcaoCode(icao2)
		    .withLatitude(latitude2)
		    .withLongitude(longitude2);

		List<Airport> airports = new ArrayList<>();
		airports.add(firstAirport);
		airports.add(secondAirport);
		String csvContent = this.createValidCsvContentForAirports(airports);

		InputStream inputStream = new ByteArrayInputStream(
		    csvContent.getBytes(StandardCharsets.UTF_8));

		// When
		List<Airport> actualAirports = cut.deserialize(inputStream);

		// Then
		assertThat(actualAirports.size()).isEqualTo(2);
		assertThat(actualAirports.contains(firstAirport));
		assertThat(actualAirports.contains(secondAirport));
	}

	private String createValidCsvContentForAirports(List<Airport> airports) {
		StringBuilder builder = new StringBuilder();
		for (Airport airport : airports) {
			// @formatter:off
			builder.append(EMPTY)
			    .append(SEPARATOR)  // ID
			    .append(airport.getName())
			    .append(SEPARATOR)
			    .append(airport.getCity())
			    .append(SEPARATOR)
			    .append(airport.getCountry())
			    .append(SEPARATOR)
			    .append(airport.getIataCode())
			    .append(SEPARATOR)
			    .append(airport.getIcaoCode())
			    .append(SEPARATOR)
			    .append(airport.getLatitude())
			    .append(SEPARATOR)
			    .append(EMPTY)
			    .append(SEPARATOR) // Altidude
			    .append(EMPTY)
			    .append(SEPARATOR) // Timezone
			    .append(EMPTY)
			    .append(SEPARATOR) // DST
			    .append(EMPTY)
			    .append(SEPARATOR) // database time
			    .append(EMPTY)
			    .append(SEPARATOR) // type
			    .append(EMPTY)
			    .append(SEPARATOR) // source
			    .append(airport.getLongitude())
			    .append("\n");
			// @formatter:on
		}
		return builder.toString();
	}
}
