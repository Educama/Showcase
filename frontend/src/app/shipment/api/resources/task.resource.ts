import {Party} from "../datastructures/party.datastructure";
export class TaskResource {
    public createTime: Date;
    public trackingId: string;
    public taskId: string;
    public name: string;
    public description: string;
    public assignee: string;
    public customer: string;
    public sender: Party;
    public receiver: Party;
}
