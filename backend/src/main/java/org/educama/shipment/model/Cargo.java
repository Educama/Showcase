package org.educama.shipment.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * This represents the address entity used for database persistence.
 */
@Embeddable
public class Cargo {

    @NotNull
    public int numberPackages;

    @NotNull
    public double totalWeight;

    @NotNull
    public double totalCapacity;

    @NotNull
    public String cargoDescription;

    @NotNull
    public boolean dangerousGoods;

    /**
     * Constructor for JPA.
     */
    public Cargo() {
        //empty
    }

    public Cargo(int numberPackages, double totalWeight, double totalCapacity, String cargoDescription, boolean dangerousGoods) {
        this.numberPackages = numberPackages;
        this.totalWeight = totalWeight;
        this.totalCapacity = totalCapacity;
        this.cargoDescription = cargoDescription;
        this.dangerousGoods = dangerousGoods;
    }
}

