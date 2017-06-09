package org.educama.services.flightinformation.businessservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.educama.services.flightinformation.datafeed.AirlineCsvDeserializer;
import org.educama.services.flightinformation.model.Airline;
import org.educama.services.flightinformation.repository.AirlineRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class AirlineBusinessServiceUnitTest {

	@Mock
	AirlineRepository airlineRepository;

	@Mock
	MultipartFile file;

	@Mock
	InputStream inputStream;

	@Mock
	AirlineCsvDeserializer airlineCsvDeserializer;

	@Mock
	List<Airline> airlines;

	@InjectMocks
	private AirlineBusinessService cut;

	private final String iataLowerCase = "ana";

	private final String iataUpperCase = "ANA";

	@Test
	public void findAirlinesByIataCode_retrievesAirlines_irrespectiveOfTheCaseOfIATA() {

		// When
		cut.findAirlinesByIataCode(iataLowerCase);

		// Then
		verify(airlineRepository).findByIataCode(iataUpperCase);
	}

	@Test
	public void findAirlinesSuggestionsByIataCode_retrievesAirlinses_irrespectiveOfTheCaseOfIATA() {
		// When
		cut.findAirlinesSuggestionsByIataCode(iataLowerCase);
		// Then
		verify(airlineRepository).findByIataCodeLike(iataUpperCase);
	}

	@Test
	public void findAirlinesSuggestionsByIataCode_returnsAnEmptyList_whenNoTermSpecified() {
		// When
		List<Airline> suggestions = cut.findAirlinesSuggestionsByIataCode(null);
		// Then
		assertThat(suggestions).isEmpty();

		// When
		suggestions = cut.findAirlinesSuggestionsByIataCode("");
		// Then
		assertThat(suggestions).isEmpty();
	}

	@Test
	public void findAirlinesSuggestionsByCallsign_returnsAnEmptyList_whenNoTermSpecified() {
		// When
		List<Airline> suggestions = cut.findAirlinesSuggestionsByCallSign(null);
		// Then
		assertThat(suggestions).isEmpty();

		// When
		suggestions = cut.findAirlinesSuggestionsByCallSign("");
		// Then
		assertThat(suggestions).isEmpty();
	}

	@Test
	public void findAirlinesSuggestionsByIataCode_truncatesTheResultSet_whenMoreThanMaximumFound() {
		// Given
		final String term = "iata";

		List<Airline> airlines = new ArrayList<>();
		for (int i = 1; i <= AirportBusinessService.maxSuggestions + 2; i++) {

			airlines.add(new Airline().withIataCode(term + i));
		}
		when(airlineRepository.findByIataCodeLike(term.toUpperCase())).thenReturn(airlines);

		// When
		List<Airline> suggestions = cut.findAirlinesSuggestionsByIataCode(term);
		// Then
		assertThat(suggestions.size()).isEqualTo(AirportBusinessService.maxSuggestions);
	}

	@Test
	public void findAirlinesSuggestionsByCallSign_truncatesTheResultSet_whenMoreThanMaximumFound() {
		// Given
		final String term = "callSign";

		List<Airline> airlines = new ArrayList<>();
		for (int i = 1; i <= AirportBusinessService.maxSuggestions + 2; i++) {

			airlines.add(new Airline().withIataCode(term + i));
		}
		when(airlineRepository.findByCallSignLike(term.toUpperCase())).thenReturn(airlines);

		// When
		List<Airline> suggestions = cut.findAirlinesSuggestionsByCallSign(term);
		// Then
		assertThat(suggestions.size()).isEqualTo(AirportBusinessService.maxSuggestions);
	}

	@Test
	public void importAirlines_replacesTheContentOfRepository() throws IOException {
		// Given
		when(file.getInputStream()).thenReturn(inputStream);
		when(airlineCsvDeserializer.deserialize(inputStream)).thenReturn(airlines);

		// When
		cut.clearAndImportAirlines(file);

		// Then
		verify(airlineRepository).deleteAll();
		verify(airlineRepository).save(airlines);

	}

	@Test
	public void findAllAirlines_returnsAllAirlines() {

		// When
		cut.findAllAirlines();

		// Then
		verify(airlineRepository).findAll();
	}

}
