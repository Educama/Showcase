package org.educama.airport.datafeed;

import org.educama.airport.model.Airport;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Component
public class AirportCsvDeserializer {


    public List<Airport> deserialize(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        ICsvBeanReader csvBeanReader = new CsvBeanReader(inputStreamReader, CsvPreference.STANDARD_PREFERENCE);
        final String[] header = {null, "name", "city", "country", "iataCode", "icaoCode", "latitude", "longitude", null, null, null, null, null, null};
        Airport airport = null;
        List<Airport> airports = new ArrayList<>();
        try {

            while ((airport = csvBeanReader.read(Airport.class, header, getCellProcessors())) != null) {
                System.out.println("deserialized " + airport);
                airports.add(airport);
            }
        } finally {
            if (csvBeanReader != null) {
                csvBeanReader.close();
            }
        }
        return airports;
    }

    private CellProcessor[] getCellProcessors() {
        return new CellProcessor[]{
                // @formatter:off
		    null, // airport ID
		    new Optional(), // name
		    new Optional(), // city
		    new Optional(), // country
		    new Optional(), // iataCode
		    new Optional(), // icaoCode
		    new Optional(new ParseDouble()), // latitude
		    new Optional(new ParseDouble()), // longitude
		    null, // altitude
		    null, // Timezone
		    null, // DST
		    null, // Tz database time zone
		    null, // Type
		    null, // Source
			// @formatter:on
        };

    }
}
