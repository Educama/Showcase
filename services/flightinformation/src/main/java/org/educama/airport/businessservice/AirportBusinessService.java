package org.educama.airport.businessservice;

import org.educama.airport.datafeed.AirportCsvDeserializer;
import org.educama.airport.model.Airport;
import org.educama.airport.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportBusinessService {


    private AirportRepository airportRepository;
    private AirportCsvDeserializer airportCsvDeserializer;

    protected static int maxSuggestions = 10;

    @Autowired
    public AirportBusinessService(AirportRepository airportRepository, AirportCsvDeserializer airportCsvDeserializer) {
        this.airportRepository = airportRepository;
        this.airportCsvDeserializer = airportCsvDeserializer;
    }

    public Page<Airport> findAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    public List<Airport> findAirportByIataCode(String iataCode) {
        if (StringUtils.isEmpty(iataCode)) {
            return Collections.emptyList();
        }
        return airportRepository.findByIataCode(iataCode.toUpperCase());
    }

    public List<Airport> findAirportByIcaoCode(String icaoCode) {
        if (StringUtils.isEmpty(icaoCode)) {
            return Collections.emptyList();
        }
        return airportRepository.findByIcaoCode(icaoCode.toUpperCase());
    }

    public List<Airport> findAirportsSuggestionsByIataCode(String iataCode) {
        if (StringUtils.isEmpty(iataCode)) {
            return Collections.emptyList();
        }

        List<Airport> suggestions = airportRepository.findByIataCodeLike(iataCode.toUpperCase());

        return suggestions.stream()
                .limit(maxSuggestions)
                .collect(Collectors.toList());
    }

    public void clearAndImportAirports(MultipartFile file) throws IOException {
        List<Airport> airports = airportCsvDeserializer.deserialize(file.getInputStream());

        airportRepository.deleteAll();
        airportRepository.save(airports);

    }

}
