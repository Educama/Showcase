import {Component, Input, Output, EventEmitter} from "@angular/core";
import {CustomerResource} from "../api/resources/customer.resource";
import {TranslateService} from "ng2-translate/ng2-translate";
import {LazyLoadEvent} from "primeng/components/common/api";

@Component({
    selector: "educama-customer-list",
    templateUrl: "customer-list.component.html"
})
export class CustomerListComponent {

    @Input()
    public customerList: CustomerResource[];
    @Input()
    public pageNumber: number;
    @Input()
    public pageSize: number;
    @Input()
    public totalPages: number;

    @Output()
    public loadUsersEvent: EventEmitter<LazyLoadEvent> = new EventEmitter();
    @Output()
    public customerSelectedEvent: EventEmitter<string> = new EventEmitter();

    public emptyListMessage: string;

    public selectedCustomer: CustomerResource;

    constructor(_translateService: TranslateService) {
        _translateService.get("GENERIC_NO-RECORDS-FOUND")
            .subscribe(value => this.emptyListMessage = value);
    }

    public loadCustomersLazy(event: LazyLoadEvent) {
        this.loadUsersEvent.emit(event);
    }

    public onRowSelect(event: Event) {
        this.customerSelectedEvent.emit(this.selectedCustomer.uuid);
    }
}
