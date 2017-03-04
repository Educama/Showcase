import {Address} from "../datastructures/address.datastructure";

export class CustomerResource {
    public uuid: string;
    public version: string;
    public name: string;
    public address: Address;
}