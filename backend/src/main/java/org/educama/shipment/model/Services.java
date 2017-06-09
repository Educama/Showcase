package org.educama.shipment.model;

import javax.persistence.Embeddable;

/**
 * This represents the address entity used for database persistence.
 */
@Embeddable
public class Services {

    public boolean preCarriage;

    public boolean exportInsurance;

    public boolean exportCustomsClearance;

    public boolean flight;

    public boolean importInsurance;

    public boolean importCustomsClearance;

    public boolean onCarriage;

    /**
     * Constructor for JPA.
     */
    public Services() {
        //empty
    }

    public Services(boolean preCarriage, boolean exportInsurance, boolean exportCustomsClearance, boolean flight, boolean importInsurance, boolean importCustomsClearance, boolean onCarriage) {
        this.preCarriage = preCarriage;
        this.exportInsurance = exportInsurance;
        this.exportCustomsClearance = exportCustomsClearance;
        this.flight = flight;
        this.importInsurance = importInsurance;
        this.importCustomsClearance = importCustomsClearance;
        this.onCarriage = onCarriage;
    }
}

