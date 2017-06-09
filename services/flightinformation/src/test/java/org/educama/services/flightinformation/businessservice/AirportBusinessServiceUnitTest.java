package org.educama.services.flightinformation.businessservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.educama.services.flightinformation.datafeed.AirportCsvDeserializer;
import org.educama.services.flightinformation.model.Airport;
import org.educama.services.flightinformation.repository.AirportRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class AirportBusinessServiceUnitTest {

	@Mock
	AirportRepository airportRepository;

	@Mock
	MultipartFile file;

	@Mock
	InputStream inputStream;

	@Mock
	AirportCsvDeserializer airportCsvDeserializer;

	@Mock
	List<Airport> airportList;

	@InjectMocks
	private AirportBusinessService cut;

	private final String iataLowerCase = "ybb";

	private final String iataUpperCase = "YBB";

	@Test
	public void getAirport_retrievesAirport_irrespectiveOfTheCaseOfIATA() {

		// When
		cut.findAirportByIataCode(iataLowerCase);

		// Then
		verify(airportRepository).findByIataCode(iataUpperCase);
	}

	@Test
	public void getAirportSuggestions_retrievesAirports_irrespectiveOfTheCaseOfIATA() {
		// When
		cut.findAirportsSuggestionsByIataCode(iataLowerCase);
		// Then
		verify(airportRepository).findByIataCodeLike(iataUpperCase);
	}

	@Test
	public void getSuggestions_returnsAnEmptyList_whenNoTermSpecified() {
		// When
		List<Airport> suggestions = cut.findAirportsSuggestionsByIataCode(null);
		// Then
		assertThat(suggestions).isEmpty();

		// When
		suggestions = cut.findAirportsSuggestionsByIataCode("");
		// Then
		assertThat(suggestions).isEmpty();
	}

	@Test
	public void getSuggestions_truncatesTheResultSet_whenMoreThanMaximumFound() {
		// Given
		final String term = "iata";

		List<Airport> airports = new ArrayList<>();
		for (int i = 1; i <= AirportBusinessService.maxSuggestions + 2; i++) {

			airports.add(new Airport().withIataCode(term + i));
		}
		when(airportRepository.findByIataCodeLike(term.toUpperCase())).thenReturn(airports);

		// When
		List<Airport> suggestions = cut.findAirportsSuggestionsByIataCode(term);
		// Then
		assertThat(suggestions.size()).isEqualTo(AirportBusinessService.maxSuggestions);
	}

	@Test
	public void importAirport_replaceTheContentOfRepository() throws IOException {
		// Given
		when(file.getInputStream()).thenReturn(inputStream);
		when(airportCsvDeserializer.deserialize(inputStream)).thenReturn(airportList);

		// When
		cut.clearAndImportAirports(file);

		// Then
		verify(airportRepository).deleteAll();
		verify(airportRepository).save(airportList);

	}

	@Test
	public void getAllAirports_returnsAllAirports() {

		// When
		cut.findAllAirports();

		// Then
		verify(airportRepository).findAll();
	}

}
