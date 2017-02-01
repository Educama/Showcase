export class Address {
    public street: string;
    public streetNo: string;
    public zipCode: string;
    public city: string;

    constructor(street: string, streetNo: string, zipCode: string, city: string) {
        this.street = street;
        this.streetNo = streetNo;
        this.zipCode = zipCode;
        this.city = city;
    }
}