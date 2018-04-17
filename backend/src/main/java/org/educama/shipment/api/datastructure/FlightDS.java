package org.educama.shipment.api.datastructure;

import org.educama.shipment.model.Flight;

/**
 * Re-usable data structure used by resources.
 */
public class FlightDS {

    public String flightNumber;
    public String airline;
    public String departureAirport;
    public String destinationAirport;
    public String departureTime;
    public String destinationTime;
    public double price;

    private FlightDS() {
        // empty
    }

    public FlightDS(Flight flight) {
        this.flightNumber = flight.flightNumber;
        this.airline = flight.airline;
        this.departureAirport = flight.departureAirport;
        this.destinationAirport = flight.destinationAirport;
        this.departureTime = flight.departureTime == null ? null : flight.departureTime.toString();
        this.destinationTime = flight.destinationTime == null ? null : flight.destinationTime.toString();
        this.price = flight.price;
    }

    public Flight toFlight() {
                return new Flight(this.flightNumber, this.airline, this.departureAirport, this.destinationAirport, this.departureTime, this.destinationTime, this.price);
    }
}
