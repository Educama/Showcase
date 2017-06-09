package org.educama.services.flightinformation.datafeed;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.educama.services.flightinformation.model.Airline;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

@Component
public class AirlineCsvDeserializer implements CsvDeserializer<Airline> {

	@Override
	public List<Airline> deserialize(InputStream in) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		ICsvBeanReader csvBeanReader = new CsvBeanReader(inputStreamReader, CsvPreference.STANDARD_PREFERENCE);
		final String[] header = { null, "name", "alias", "iataCode", "icaoCode", "callsign", "country", null };
		Airline airline = null;
		List<Airline> airlines = new ArrayList<>();
		try {

			while ((airline = csvBeanReader.read(Airline.class, header, getCellProcessors())) != null) {
				System.out.println("deserialized " + airline);
				airlines.add(airline);
			}
		} finally {
			if (csvBeanReader != null) {
				csvBeanReader.close();
			}
		}
		return airlines;
	}

	private CellProcessor[] getCellProcessors() {
		return new CellProcessor[] {
		    // @formatter:off
		    null, // airline ID
		    new Optional(), // name
		    new Optional(), // alias
		    new Optional(), // iataCode
		    new Optional(), // icaoCode
		    new Optional(), // callsign
		    new Optional(), // country
		    null, // active
			// @formatter:on
		};

	}
}
