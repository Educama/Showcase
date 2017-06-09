package org.educama.flightconnection.datafeed;


import org.educama.App;
import org.educama.airline.model.Airline;
import org.educama.airline.repository.AirlineRepository;
import org.educama.airport.model.Airport;
import org.educama.airport.repository.AirportRepository;
import org.educama.flightconnection.model.Connection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.supercsv.exception.SuperCsvCellProcessorException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ConnectionCsvDeserializerIntTest {
    private static final String SEPARATOR = ",";
    private static final String EMPTY = "";
    private static final Charset CSV_CHARSET = StandardCharsets.ISO_8859_1;


    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    ConnectionCsvDeserializer cut;

    @Before
    public void tearDown() {
        List<Airport> airports = airportRepository.findByIataCode(getDefaultAirport().getIataCode());
        List<Airline> airlines = airlineRepository.findByIataCode(getDefaultAirline().getIataCode());
        if (!airports.isEmpty()) {
            airportRepository.delete(airports);
        }
        if (!airlines.isEmpty()) {
            airlineRepository.delete(airlines);
        }


    }


    @Test
    public void deserialize_throwsException_whenNodValidCodeShare() throws IOException {
        //Given
        boolean thrown = false;
        final String invalidCodeshare = "Invalid codeshare";
        final Connection connection = new Connection().withAirlineIataCode("LH")
                .withSourceAirportIataCode("FRA")
                .withDestinationAirportIataCode("LAX")
                .withStops(0);
        List<Connection> connections = new ArrayList<>();
        connections.add(connection);

        StringBuilder builder = new StringBuilder();
        builder.append(connection.getAirlineIataCode())
                .append(SEPARATOR)  //ID
                .append(EMPTY)
                .append(SEPARATOR)  //airline ID
                .append(connection.getSourceAirportIataCode())
                .append(SEPARATOR)
                .append(EMPTY)
                .append(SEPARATOR) //source ID
                .append(connection.getDestinationAirportIataCode())
                .append(SEPARATOR)
                .append(EMPTY)
                .append(SEPARATOR) //destination ID
                .append(invalidCodeshare)
                .append(SEPARATOR)
                .append(connection.getStops())
                .append(SEPARATOR)
                .append(EMPTY)
                .append("\n");     // Equipment
        //@formatter:on
        String csvContent = builder.toString();


        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        try {
            cut.deserialize(inputStream);
        }
        // ensure that the proper cell processor what activated to reject the invalid codeshare
        catch (SuperCsvCellProcessorException e) {
            thrown = true;
            assertThat(e.getMessage()).containsIgnoringCase(invalidCodeshare);
        }
        assertThat(thrown).isTrue();


    }

    @Test
    public void deserialize_throwsException_whenInvalidAirportCodeLength() throws IOException {
        //Given
        final String invalidAirportCode = "invalid airport code";
        boolean thrown = false;
        Connection connection = getDefaultConnection();
        connection.setSourceAirportIataCode(invalidAirportCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));
        //When
        try {
            cut.deserialize(inputStream);
        }
        //Then: ensure that the proper cell processor what activated to reject the invalid airport code
        catch (SuperCsvCellProcessorException e) {

            thrown = true;
            assertThat(e.getMessage()).containsIgnoringCase(invalidAirportCode);
        }
        assertThat(thrown).isTrue();

    }

    @Test
    public void deserialize_throwsException_whenInvalidAirlineCodeLength() throws IOException {
        //Given
        final String invalidAirlineCode = "invalid airline code";
        boolean thrown = false;
        Connection connection = getDefaultConnection();
        connection.setDestinationAirportIataCode(invalidAirlineCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);
        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));
        //When
        try {
            cut.deserialize(inputStream);
        }
        //Then: ensure that the proper cell processor what activated to reject the invalid airline code
        catch (SuperCsvCellProcessorException e) {

            thrown = true;
            assertThat(e.getMessage()).containsIgnoringCase(invalidAirlineCode);
        }
        assertThat(thrown).isTrue();

    }

    @Test
    public void deserialize_convertsKnownAirportsIcaoCodeIntoCorrespondingIataCode() throws IOException {
        //Given
        Airport airport = getDefaultAirport();
        final String iataCode = airport.getIataCode();
        final String icaoCode = airport.getIcaoCode();
        airportRepository.save(airport);

        Connection connection = getDefaultConnection();
        connection.setSourceAirportIataCode(icaoCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        List<Connection> actualConnections = cut.deserialize(inputStream);

        //Then
        assertThat(actualConnections.size()).isEqualTo(1);
        assertThat(actualConnections.get(0)
                .getSourceAirportIataCode()).isEqualTo(iataCode);

    }

    @Test
    public void deserialize_doesNotConvertsUnknownAirportsIcaoIntoIataCode() throws IOException {
        //Given
        Airport airport = getDefaultAirport();

        final String unknownIcaoCode = "IZZZ";
        airportRepository.save(airport);

        Connection connection = getDefaultConnection();
        connection.setSourceAirportIataCode(unknownIcaoCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        List<Connection> actualConnections = cut.deserialize(inputStream);

        //Then
        assertThat(actualConnections.size()).isEqualTo(1);
        assertThat(actualConnections.get(0)
                .getSourceAirportIataCode()).isEqualTo(unknownIcaoCode
        );

    }

    @Test
    public void deserialize_doesNotConvertsUnknownAirlinesIcaoIntoIataCode() throws IOException {
        //Given
        Airline airline = getDefaultAirline();

        final String unknownIcaoCode = "IZZ";
        airlineRepository.save(airline);

        Connection connection = getDefaultConnection();
        connection.setAirlineIataCode(unknownIcaoCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        List<Connection> actualConnections = cut.deserialize(inputStream);

        //Then
        assertThat(actualConnections.size()).isEqualTo(1);
        assertThat(actualConnections.get(0)
                .getAirlineIataCode()).isEqualTo(unknownIcaoCode
        );

    }

    @Test
    public void deserialize_convertsKnownAirlinesIcaoIntoCorrespondingIataCode() throws IOException {
        //Given
        Airline airline = getDefaultAirline();
        final String iataCode = airline.getIataCode();
        final String icaoCode = airline.getIcaoCode();
        airlineRepository.save(airline);

        Connection connection = getDefaultConnection();
        connection.setAirlineIataCode(icaoCode);

        List<Connection> connections = new ArrayList<>();
        connections.add(connection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        List<Connection> actualConnections = cut.deserialize(inputStream);

        //Then
        assertThat(actualConnections.size()).isEqualTo(1);
        assertThat(actualConnections.get(0)
                .getAirlineIataCode()).isEqualTo(iataCode);

    }

    @Test
    public void deserialize_createsNewConnectionInstances_WhenValidCsvInput() throws IOException {
        //Given
        Connection expectedConnection = getDefaultConnection();

        List<Connection> connections = new ArrayList<>();
        connections.add(expectedConnection);
        String csvContent = cut.createCsvContentForConnections(connections);

        InputStream inputStream = new ByteArrayInputStream(
                csvContent.getBytes(CSV_CHARSET));

        //When
        List<Connection> actualConnections = cut.deserialize(inputStream);

        //Then
        assertThat(actualConnections.size()).isEqualTo(1);
        Connection actualConnection = actualConnections.get(0);
        assertThat(actualConnection.getAirlineIataCode()).isEqualTo(expectedConnection.getAirlineIataCode());
        assertThat(actualConnection.getSourceAirportIataCode()).isEqualTo(expectedConnection.getSourceAirportIataCode());
        assertThat(actualConnection.getDestinationAirportIataCode()).isEqualTo(expectedConnection.getDestinationAirportIataCode());


    }

    private Connection getDefaultConnection() {
        return new Connection().withAirlineIataCode("UA")
                .withSourceAirportIataCode("JFK")
                .withDestinationAirportIataCode("MUC")
                .withCodeShare(true)
                .withStops(0);
    }

    private Airport getDefaultAirport() {
        return new Airport().withIataCode("XXX")
                .withIcaoCode("IYYY");
    }

    private Airline getDefaultAirline() {
        return new Airline().withIataCode("XX")
                .withIcaoCode("IYY")
                .withName("test Airline")
                .withCallSign("test")
                .withCountry("test country");

    }

}
