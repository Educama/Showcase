package org.educama.shipment.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * This represents the address entity used for database persistence.
 */
@Embeddable
public class Services {

    @NotNull
    public boolean preCarriage;

    @NotNull
    public boolean exportInsurance;

    @NotNull
    public boolean exportCustomsClearance;

    @NotNull
    public boolean flight;

    @NotNull
    public boolean importInsurance;

    @NotNull
    public boolean importCustomsClearance;

    @NotNull
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

