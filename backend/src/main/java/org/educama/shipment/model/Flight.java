package org.educama.shipment.model;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.Instant;

/**
 * This represents the address entity used for database persistence.
 */
@Embeddable
public class Flight {

    public String flightNumber;
    public String airline;
    public String departureAirport;
    public String destinationAirport;
    @Convert(converter = InstantConverter.class)
    public Instant departureTime;
    @Convert(converter = InstantConverter.class)
    public Instant destinationTime;
    public double price;


    /**
     * Constructor for JPA.
     */
    public Flight() {
        //empty
    }

    public Flight(String flightNumber, String airline, String departureAirport, String destinationAirport, String departureTime, String destinationTime, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureTime = Instant.parse(departureTime);
        this.destinationTime = Instant.parse(destinationTime);
        this.price = price;
    }
}

