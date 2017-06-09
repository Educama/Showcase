package org.educama.services.flightinformation.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.educama.services.flightinformation.model.Airport;
import org.junit.Test;

public class AirportUnitTest {

	Airport firstAirport = new Airport().withName("Los Angeles International Airport")
	    .withCity("Los Angeles")
	    .withCountry("USA")
	    .withIataCode("LAX")
	    .withIcaoCode("KLAX")
	    .withLatitude(3)
	    .withLongitude(-4);

	Airport secondAirport = new Airport().withName("LA airport")
	    .withCity("LA")
	    .withCountry("United States")
	    .withIataCode("lax")
	    .withIcaoCode("foobar")
	    .withLatitude(0)
	    .withLongitude(2);

	@Test
	public void equals_returnsTrue_whenSameIataCode_irrespectiveOfCase() {

		assertThat(firstAirport.equals(secondAirport)).isTrue();

	}

	@Test
	public void hashcode_returnSameValue_whenSameIataCode_irrespectiveOfCase() {
		assertThat(firstAirport.hashCode()).isEqualTo(secondAirport.hashCode());
	}

	@Test
	public void iataCode_isAlwaysConvertedToUppercase() {
		// Given
		Airport firstAirport = new Airport();
		Airport secondAirport;

		// When
		firstAirport.setIataCode("nsi");
		secondAirport = new Airport().withIataCode("dla");

		// Then
		assertThat(firstAirport.getIataCode()).isEqualTo("NSI");
		assertThat(secondAirport.getIataCode()).isEqualTo("DLA");
	}
}
