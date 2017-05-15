import {Address} from "../../api/datastructures/address.datastructure";

export class SaveCustomerEvent {
    uuid?: string;
    name: string;
    address: Address;

    constructor(name: string, address: Address, uuid?: string) {
        this.name = name;
        this.address = address;
        this.uuid = uuid;
    }
}