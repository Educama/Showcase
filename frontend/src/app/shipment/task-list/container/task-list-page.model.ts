export class TaskListModel {
    public taskList: TaskListRowModel[];
}

export class TaskListRowModel {
    public createTime: Date;
    public trackingId: string;
    public taskId: string;
    public name: string;
    public description: string;
    public assignee: string;
    public senderAddress: string;
    public receiverAddress: string;

    constructor(createTime: Date,
                trackingId: string,
                taskId: string,
                name: string,
                description: string,
                assignee: string,
                senderAddress: string,
                receiverAddress: string) {
        this.createTime = createTime;
        this.trackingId = trackingId;
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }
}
