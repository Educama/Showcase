package org.educama.shipment.api.datastructure;

import javax.validation.constraints.NotNull;
import org.educama.shipment.model.Cargo;

/**
 * Re-usable data structure used by resources.
 */
public class CargoDS {

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
    /*
     * For Jackson Parser
     */
    private CargoDS() {
        //empty
    }

    public CargoDS(Cargo cargo) {
        this.numberPackages = cargo.numberPackages;
        this.totalWeight = cargo.totalWeight;
        this.totalCapacity = cargo.totalCapacity;
        this.cargoDescription = cargo.cargoDescription;
    }

    public CargoDS(int numberPackages, double totalWeight, double totalCapacity, String description, boolean dangerousGoods) {
        this.numberPackages = numberPackages;
        this.totalWeight = totalWeight;
        this.totalCapacity = totalCapacity;
        this.cargoDescription = cargoDescription;
        this.dangerousGoods = dangerousGoods;
    }

    public Cargo toCargo() {
        return new Cargo(this.numberPackages, this.totalWeight, this.totalCapacity, this.cargoDescription, this.dangerousGoods);
    }

}
