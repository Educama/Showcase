package org.educama.flightconnection.businessservice;

import org.educama.flightconnection.datafeed.ConnectionCsvDeserializer;
import org.educama.flightconnection.model.Connection;
import org.educama.flightconnection.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class ConnectionBusinessService {

    ConnectionRepository connectionRepository;

    ConnectionCsvDeserializer connectionCsvDeserializer;

    @Autowired
    public ConnectionBusinessService(ConnectionRepository connectionRepository, ConnectionCsvDeserializer connectionCsvDeserializer) {
        this.connectionRepository = connectionRepository;
        this.connectionCsvDeserializer = connectionCsvDeserializer;
    }


    public Page<Connection> findFlightConnection(String sourceIataCode, String destinationIataCode, Pageable pageable) {
        if (StringUtils.isEmpty(sourceIataCode) || StringUtils.isEmpty(destinationIataCode)) {
            return new PageImpl<Connection>(Collections.emptyList());
        }
        return connectionRepository.findBySourceAirportIataCodeAndDestinationAirportIataCode(sourceIataCode.toUpperCase(), destinationIataCode.toUpperCase(), pageable);
    }

    public void clearAndImportConnections(MultipartFile file) throws IOException {
        List<Connection> connections = connectionCsvDeserializer.deserialize(file.getInputStream());

        connectionRepository.deleteAll();
        connectionRepository.save(connections);

    }

    public Page<Connection> findAllConnectionsFromSourceToDestionation(String sourceAirportIataCode, String destinationAirportIataCode, Pageable pageable) {
        if (StringUtils.isEmpty(sourceAirportIataCode) && StringUtils.isEmpty(destinationAirportIataCode)) {
            return new PageImpl<Connection>(Collections.emptyList());
        }
        if (!StringUtils.isEmpty(sourceAirportIataCode) && StringUtils.isEmpty(destinationAirportIataCode)) {
            return connectionRepository.findBySourceAirportIataCode(sourceAirportIataCode.toUpperCase(), pageable);
        }
        if (StringUtils.isEmpty(sourceAirportIataCode) && !StringUtils.isEmpty(destinationAirportIataCode)) {
            return connectionRepository.findBydestinationAirportIataCode(destinationAirportIataCode.toUpperCase(), pageable);
        }

        return connectionRepository.findBySourceAirportIataCodeAndDestinationAirportIataCode(sourceAirportIataCode.toUpperCase(), destinationAirportIataCode.toUpperCase(), pageable);
    }


}
