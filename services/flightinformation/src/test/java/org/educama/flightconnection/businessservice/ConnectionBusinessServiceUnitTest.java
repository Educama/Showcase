package org.educama.flightconnection.businessservice;

import org.educama.flightconnection.repository.ConnectionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionBusinessServiceUnitTest {
    Pageable pageable;
    @Mock
    ConnectionRepository connectionRepository;

    @InjectMocks
    ConnectionBusinessService cut;

    public void setUp() {
        pageable = new PageRequest(0, 1);
    }

    @Test
    public void findFlightConnection_returnsEmptyList_whenNoSourceAirport() {
        //Given
        final String destinationAirport = "LAX";
        //Then
        assertThat(cut.findFlightConnection(null, destinationAirport, pageable)).isEmpty();
        assertThat(cut.findFlightConnection("", destinationAirport, pageable)).isEmpty();
    }

    @Test
    public void findFlightConnection_returnsEmptyList_whenNoSourceAndNoDestinationAirport() {
        //Given
        final String sourceAirport = "LAX";
        //Then
        assertThat(cut.findFlightConnection(sourceAirport, null, pageable)).isEmpty();
        assertThat(cut.findFlightConnection(sourceAirport, "", pageable)).isEmpty();
    }

    @Test
    public void findFlightConnection_returnsListOfConnections() {
        //Given
        final String sourceAirport = "fra";
        final String destinationAirport = "LAX";
        //When
        cut.findFlightConnection(sourceAirport, destinationAirport, pageable);
        //Then
        verify(connectionRepository).findBySourceAirportIataCodeAndDestinationAirportIataCode(sourceAirport.toUpperCase(), destinationAirport, pageable);
    }

    @Test
    public void findFlightConnection_returnsConnectionsFromSourceAirport_whenOnlySourceAirportSpecified() {
        //Given
        final String source = "LAX";

        cut.findAllConnectionsFromSourceToDestionation(source, null, pageable);
        //Then
        verify(connectionRepository).findBySourceAirportIataCode(source.toUpperCase(), pageable);

    }

    @Test
    public void findFlightConnection_returnsConnectionsToDestinationAirport_whenOnlyDestinationAirportSpecified() {
        //Given
        final String destination = "LAX";
        //When
        cut.findAllConnectionsFromSourceToDestionation(null, destination, pageable);
        //Then
        verify(connectionRepository).findBydestinationAirportIataCode(destination.toUpperCase(), pageable);
    }


}
