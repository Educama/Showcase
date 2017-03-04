import {Address} from "../../api/datastructures/address.datastructure";

export class SaveCustomerEvent {
    uuid?: string;
    version: string;
    name: string;
    address: Address;

    constructor(name: string, version: string, address: Address, uuid?: string) {
        this.name = name;
        this.version = version;
        this.address = address;
        this.uuid = uuid;
    }
}