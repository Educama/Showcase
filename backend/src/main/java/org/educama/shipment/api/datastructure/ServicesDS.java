package org.educama.shipment.api.datastructure;

import org.educama.shipment.model.Services;

/**
 * Re-usable data structure used by resources.
 */
public class ServicesDS {

    public boolean preCarriage;

    public boolean exportInsurance;

    public boolean exportCustomsClearance;

    public boolean flight;

    public boolean importInsurance;

    public boolean importCustomsClearance;

    public boolean onCarriage;

    /*
     * For Jackson Parser
     */
    private ServicesDS() {
        //empty
    }

    public ServicesDS(Services services) {
        this.preCarriage = services.preCarriage;
        this.exportInsurance = services.exportInsurance;
        this.exportCustomsClearance = services.exportCustomsClearance;
        this.flight = services.flight;
        this.importInsurance = services.importInsurance;
        this.importCustomsClearance = services.importCustomsClearance;
        this.onCarriage = services.onCarriage;
    }

    public ServicesDS(boolean preCarriage, boolean exportInsurance, boolean exportCustomsClearance, boolean flight, boolean importInsurance, boolean importCustomsClearance, boolean onCarriage) {
        this.preCarriage = preCarriage;
        this.exportInsurance = exportInsurance;
        this.exportCustomsClearance = exportCustomsClearance;
        this.flight = flight;
        this.importInsurance = importInsurance;
        this.importCustomsClearance = importCustomsClearance;
        this.onCarriage = onCarriage;
    }

    public Services toServices() {
        return new Services(this.preCarriage, this.exportInsurance, this.exportCustomsClearance, this.flight, this.importInsurance, this.importCustomsClearance, this.onCarriage);
    }

}
