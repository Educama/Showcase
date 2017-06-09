package org.educama.flightconnection.datafeed;


import org.educama.airline.businessservice.AirlineBusinessService;
import org.educama.airline.model.Airline;
import org.educama.airport.businessservice.AirportBusinessService;
import org.educama.airport.model.Airport;
import org.educama.flightconnection.model.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Component
public class ConnectionCsvDeserializer {
    private String CODESHARE_YES = "Y";
    private String CODESHARE_NO = "";
    private String SEPARATOR = ",";
    private String EMPTY = "";

    private AirportBusinessService airportBusinessService;

    private AirlineBusinessService airlineBusinessService;

    @Autowired
    public ConnectionCsvDeserializer(AirportBusinessService airportBusinessService, AirlineBusinessService airlineBusinessService) {
        this.airportBusinessService = airportBusinessService;
        this.airlineBusinessService = airlineBusinessService;
    }

    public List<Connection> deserialize(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        ICsvBeanReader csvBeanReader = new CsvBeanReader(inputStreamReader, CsvPreference.STANDARD_PREFERENCE);
        final String[] header = {"airlineIataCode", null, "sourceAirportIataCode", null, "destinationAirportIataCode", null, "codeshare", "stops", null};
        Connection connection = null;
        List<Connection> connections = new ArrayList<>();
        try {

            while ((connection = csvBeanReader.read(Connection.class, header, getCellProcessors())) != null) {
                System.out.println("deserialized " + connection);
                connections.add(connection);
            }
        } finally {
            if (csvBeanReader != null) {
                csvBeanReader.close();
            }
        }
        return connections;
    }

    public String createCsvContentForConnections(List<Connection> connections) {
        StringBuilder builder = new StringBuilder();
        for (Connection connection : connections) {
            String codeShare = connection.isCodeshare() ? CODESHARE_YES : CODESHARE_NO;
            //@formatter:off
            builder.append(connection.getAirlineIataCode())               .append(SEPARATOR)  //ID
                    .append(EMPTY)                                        .append(SEPARATOR)  //airline ID
                    .append(connection.getSourceAirportIataCode())        .append(SEPARATOR)
                    .append(EMPTY)                                        .append(SEPARATOR) //source ID
                    .append(connection.getDestinationAirportIataCode())   .append(SEPARATOR)
                    .append(EMPTY)                                        .append(SEPARATOR) //destination ID
                    .append(codeShare)            .append(SEPARATOR)
                    .append(connection.getStops())                        .append(SEPARATOR)
                    .append(EMPTY)                                        .append("\n");     // Equipment
            //@formatter:on
        }
        return builder.toString();
    }

    private CellProcessor[] getCellProcessors() {
        return new CellProcessor[]{
                //@formatter:off
                new Optional(new ParseAirlinesIataCode())    , //airline
                null                                         , //airline ID
                new Optional(new ParseAirportIataCode())     , //source airport
                null                                         , //source airport ID
                new Optional(new ParseAirportIataCode())     , //destination airport
                null                                         , //destination airport ID
                new Optional(new ParseCodeShare())           , //codeShare
                new Optional(new ParseInt())                 , //stops
                null                                         , //equipment
                //@formatter:ons
        };

    }

    //custom CellProcessor for parsing the codeshares
    private class ParseCodeShare extends CellProcessorAdaptor {


        public ParseCodeShare() {
            super();
        }

        public ParseCodeShare(CellProcessorAdaptor next) {
            super(next);
        }

        @Override
        public Object execute(Object value, CsvContext context) {
            validateInputNotNull(value, context);

            if (CODESHARE_YES.equalsIgnoreCase((String) value)) {
                return next.execute(Boolean.TRUE, context);
            } else {
                if (CODESHARE_NO.equalsIgnoreCase((String) value)) {
                    return next.execute(Boolean.FALSE, context);
                }
            }
            throw new SuperCsvCellProcessorException(
                    String.format("Could not parse '%s' as a codeshare", value),
                    context, this);
        }
    }

    //custom CellProcessor for parsing the airports codes
    private class ParseAirportIataCode extends CellProcessorAdaptor {
        private static final int AIRPORT_IATACODE_LENGTH = 3;
        private static final int AIRPORT_ICAOCODE_LENGTH = 4;

        public ParseAirportIataCode() {
            super();
        }

        public ParseAirportIataCode(CellProcessorAdaptor next) {
            super(next);
        }

        @Override
        /**
         * The airports codes contained in the csv can be IATA ( 3-letter) codes or ICAO (4-letter) codes.
         */
        public Object execute(Object value, CsvContext context) {
            validateInputNotNull(value, context);
            String airportCode = (String) value;
            if ((airportCode.length() == AIRPORT_IATACODE_LENGTH)) {
                return next.execute(value, context);
            }
            // attempt to convert the icao code to an IATA code. If not possible proceed with the code as it is.
            if (airportCode.length() == AIRPORT_ICAOCODE_LENGTH) {

                List<Airport> airports = airportBusinessService.findAirportByIcaoCode(airportCode);
                if (!airports.isEmpty()) {
                    return next.execute(airports.get(0)
                            .getIataCode(), context);
                } else {
                    return next.execute(airportCode, context);
                }
            }
            throw new SuperCsvCellProcessorException(
                    String.format("Could not parse '%s' neither as an IATA nor as an IACAO code for Airports", value),
                    context, this);
        }

    }

    //custom CellProcessor for parsing the airlines codes
    private class ParseAirlinesIataCode extends CellProcessorAdaptor {
        private static final int AIRLINE_IATACODE_LENGTH = 2;
        private static final int AIRLINE_ICAOCODE_LENGTH = 3;

        public ParseAirlinesIataCode() {
            super();
        }

        public ParseAirlinesIataCode(CellProcessorAdaptor next) {
            super(next);
        }

        @Override
        /**
         * The airlines codes contained in the csv can be IATA ( 2-letter) codes or ICAO (3-letter) codes.
         */
        public Object execute(Object value, CsvContext context) {
            validateInputNotNull(value, context);
            String airlineCode = (String) value;
            if ((airlineCode.length() == AIRLINE_IATACODE_LENGTH)) {
                return next.execute(value, context);
            }
            // attempt to convert the icao code to an IATA code. If not possible proceed with the code as it is.
            if (airlineCode.length() == AIRLINE_ICAOCODE_LENGTH) {

                List<Airline> airlines = airlineBusinessService.findAirlinesByIcaoCode(airlineCode);
                if (!airlines.isEmpty()) {
                    return next.execute(airlines.get(0)
                            .getIataCode(), context);
                } else {
                    return next.execute(airlineCode, context);
                }
            }
            throw new SuperCsvCellProcessorException(
                    String.format("Could not parse '%s' neither as an IATA nor as an IACAO code for Airlines", value),
                    context, this);
        }

    }
}
