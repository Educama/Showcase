export class ShipmentServices {
    public preCarriage: boolean;
    public exportInsurance: boolean;
    public exportCustomsClearance: boolean;
    public flight: boolean;
    public importInsurance: boolean;
    public importCustomsClearance: boolean;
    public onCarriage: boolean;

    constructor(preCarriage: boolean, exportInsurance: boolean, exportCustomsClearance: boolean, flight: boolean,
                importInsurance: boolean, importCustomsClearance: boolean, onCarriage: boolean) {
        this.preCarriage = preCarriage;
        this.exportInsurance = exportInsurance;
        this.exportCustomsClearance = exportCustomsClearance;
        this.flight = flight;
        this.importInsurance = importInsurance;
        this.importCustomsClearance = importCustomsClearance;
        this.onCarriage = onCarriage;
    }
}
