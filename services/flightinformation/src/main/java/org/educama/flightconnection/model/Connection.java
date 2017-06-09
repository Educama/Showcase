package org.educama.flightconnection.model;


import org.springframework.data.annotation.Id;

public class Connection {
    @Id
    private String id;
    private String airlineIataCode;
    private String sourceAirportIataCode;
    private String destinationAirportIataCode;
    // true if this flight is a codeshare  is, not operated by Airline, but another carrier
    private boolean codeshare;
    // Number of stops on this flight
    private int stops;


    public String getAirlineIataCode() {
        return airlineIataCode;
    }

    public void setAirlineIataCode(String airlineIataCode) {
        this.airlineIataCode = airlineIataCode != null ? airlineIataCode.toUpperCase() : airlineIataCode;
    }

    public String getSourceAirportIataCode() {
        return sourceAirportIataCode;
    }

    public void setSourceAirportIataCode(String sourceAirportIataCode) {
        this.sourceAirportIataCode = sourceAirportIataCode != null ? sourceAirportIataCode.toUpperCase() : sourceAirportIataCode;
    }

    public String getDestinationAirportIataCode() {
        return destinationAirportIataCode;
    }

    public void setDestinationAirportIataCode(String destinationAirportIataCode) {
        this.destinationAirportIataCode = destinationAirportIataCode != null ? destinationAirportIataCode.toUpperCase() : destinationAirportIataCode;
    }

    public boolean isCodeshare() {
        return codeshare;
    }

    public void setCodeshare(boolean codeshare) {
        this.codeshare = codeshare;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }


    public Connection withAirlineIataCode(String airlineIataCode) {
        this.setAirlineIataCode(airlineIataCode);
        return this;
    }

    public Connection withDestinationAirportIataCode(String destinationAirportIataCode) {
        this.setDestinationAirportIataCode(destinationAirportIataCode);
        return this;
    }

    public Connection withSourceAirportIataCode(String sourceAirportIataCode) {
        this.setSourceAirportIataCode(sourceAirportIataCode);
        return this;
    }

    public Connection withCodeShare(boolean codeShare) {
        this.setCodeshare(codeShare);
        return this;
    }

    public Connection withStops(int stops) {
        this.setStops(stops);
        return this;
    }

    @Override
    public String toString() {
        return String.format("Connection[id=%s, from='%s', to='%s',airline='%s']", id, sourceAirportIataCode, destinationAirportIataCode, airlineIataCode);
    }


}
