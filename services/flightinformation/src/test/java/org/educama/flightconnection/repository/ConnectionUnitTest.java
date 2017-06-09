package org.educama.flightconnection.repository;


import org.educama.flightconnection.model.Connection;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ConnectionUnitTest {
    @Test
    public void airlineIataCode_isAlwaysConvertedToUppercase() {
        // Given
        Connection firstConnection = new Connection();
        Connection secondConnection;

        // When
        firstConnection.setAirlineIataCode("lh");
        secondConnection = new Connection().withAirlineIataCode("ua");

        // Then
        assertThat(firstConnection.getAirlineIataCode()).isEqualTo("LH");
        assertThat(secondConnection.getAirlineIataCode()).isEqualTo("UA");
    }

    @Test
    public void destinationAirportIataCode_isAlwaysConvertedToUppercase() {
        // Given
        Connection firstConnection = new Connection();
        Connection secondConnection;

        // When
        firstConnection.setDestinationAirportIataCode("nsi");
        secondConnection = new Connection().withDestinationAirportIataCode("dla");

        // Then
        assertThat(firstConnection.getDestinationAirportIataCode()).isEqualTo("NSI");
        assertThat(secondConnection.getDestinationAirportIataCode()).isEqualTo("DLA");
    }

    @Test
    public void sourceAirportIataCode_isAlwaysConvertedToUppercase() {
        // Given
        Connection firstConnection = new Connection();
        Connection secondConnection;

        // When
        firstConnection.setDestinationAirportIataCode("nsi");
        secondConnection = new Connection().withDestinationAirportIataCode("dla");

        // Then
        assertThat(firstConnection.getDestinationAirportIataCode()).isEqualTo("NSI");
        assertThat(secondConnection.getDestinationAirportIataCode()).isEqualTo("DLA");
    }
}
