package org.educama.airline.businessservice;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.educama.airline.datafeed.AirlineCsvDeserializer;
import org.educama.airline.model.Airline;
import org.educama.airline.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Component
public class AirlineBusinessService {


    private AirlineRepository airlineRepository;


    private AirlineCsvDeserializer airlineCsvDeserializer;

    protected static int maxSuggestions = 10;

    @Autowired
    public AirlineBusinessService(AirlineRepository airlineRepository, AirlineCsvDeserializer airlineCsvDeserializer) {
        this.airlineRepository = airlineRepository;
        this.airlineCsvDeserializer = airlineCsvDeserializer;
    }

    public Page<Airline> findAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable);
    }

    public List<Airline> findAirlinesByIataCode(String iataCode) {
        if (StringUtils.isEmpty(iataCode)) {
            return Collections.emptyList();
        }
        return airlineRepository.findByIataCode(iataCode.toUpperCase());
    }

    public List<Airline> findAirlinesByIcaoCode(String icaoCode) {
        if (StringUtils.isEmpty(icaoCode)) {
            return Collections.emptyList();
        }
        return airlineRepository.findByIcaoCode(icaoCode.toUpperCase());
    }


    public List<Airline> findAirlinesSuggestionsByIataCode(String iataCode) {
        if (StringUtils.isEmpty(iataCode)) {
            return Collections.emptyList();
        }

        List<Airline> suggestions = airlineRepository.findByIataCodeLike(iataCode.toUpperCase());

        return suggestions.stream()
                .limit(maxSuggestions)
                .collect(Collectors.toList());
    }

    public List<Airline> findAirlinesSuggestionsByCallSign(String callSign) {
        if (StringUtils.isEmpty(callSign)) {
            return Collections.emptyList();
        }

        List<Airline> suggestions = airlineRepository.findByCallSignLike(callSign.toUpperCase());

        return suggestions.stream()
                .limit(maxSuggestions)
                .collect(Collectors.toList());
    }

    public void clearAndImportAirlines(MultipartFile file) throws IOException {
        List<Airline> airlines = airlineCsvDeserializer.deserialize(file.getInputStream());

        airlineRepository.deleteAll();
        airlineRepository.save(airlines);
    }

}
