package org.educama.shipment.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import java.util.Date;


/**
 * This represents the flight entity used for database persistence.
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "flightNumber") })
public class Flight extends AbstractPersistable<Long> {

    @NotNull
    @Column(unique = true)
    public String flightNumber;

    @NotNull
    public String airline;

    @NotNull
    public String departureAirport;

    @NotNull
    public String destinationAirport;

    @NotNull
    public Date departureTime;

    @NotNull
    public Date destinationTime;

    @NotNull
    public double price;


    /**
     * Constructor for JPA.
     */
    protected Flight() {
        //empty
    }

    public Flight(String flightNumber, String airline, String departureAirport, String destinationAirport,
                  Date departureTime, Date destinationTime, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureTime = departureTime;
        this.destinationTime = destinationTime;
        this.price = price;
    }
}
