export class ShipmentResource {
    public trackingId: number;
    public uuidSender: string;
    public uuidReceiver: string;
    public isCustomer: string;
    public numberPackages: number;
    public totalWeight: number;
    public totalCapacity: number;
    public cargoDescription: string;
    public dangerousGoods: boolean;
    public preCarriage: boolean;
    public exportInsurance: boolean;
    public exportCustomsClearance: boolean;
    public flight: boolean;
    public importInsurance: boolean;
    public importCustomsClearance: boolean;
    public onCarriage: boolean;
}