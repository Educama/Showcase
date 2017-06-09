package org.educama.flightconnection.controller;

import org.educama.flightconnection.businessservice.ConnectionBusinessService;
import org.educama.flightconnection.model.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ConnectionController {

    ConnectionBusinessService connectionBusinessService;

    @Autowired
    public ConnectionController(ConnectionBusinessService connectionBusinessService) {
        this.connectionBusinessService = connectionBusinessService;

    }

    /**
     * Retrieves all existing connections  from a source to a destination airport.
     * Retrieves all connection from a source (resp. destination) airport if only the source (resp. the destination) is specified.
     *
     * @param sourceAirportIata      the IATA code of the source airport
     * @param destinationAirportIata the IATA code of the destination airport
     * @return The available flight connections.
     */
    @RequestMapping(value = "/connections")
    public Page<Connection> getAllConnectionFromSourceToDestination(@RequestParam(value = "from") String sourceAirportIata, @RequestParam(value = "to") String destinationAirportIata, Pageable pageable) {
        return connectionBusinessService.findAllConnectionsFromSourceToDestionation(sourceAirportIata, destinationAirportIata, pageable);
    }


    /**
     * Replaces the content of the flight connection database with the content of the CSV file
     * the data contained in the csv File.
     *
     * @param file the import file containing the flight connections data
     */
    @RequestMapping(value = "/connections/import/csv", method = RequestMethod.POST)
    public @ResponseBody
    void importConnections(@RequestParam("file") MultipartFile file) throws IOException {
        connectionBusinessService.clearAndImportConnections(file);
    }
}
