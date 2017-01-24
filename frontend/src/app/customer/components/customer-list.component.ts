import {Component, Input, Output, EventEmitter, OnInit} from "@angular/core";
import {CustomerResource} from "../api/resources/customer.resource";
import {TranslateService} from "ng2-translate/ng2-translate";
import {LazyLoadEvent} from "primeng/components/common/api";

@Component({
    selector: "educama-customer-list",
    templateUrl: "customer-list.component.html"
})
export class CustomerListComponent implements OnInit {

    @Input()
    public customerList: CustomerResource[];
    // TODO: Combine to a PageInfo datastructure
    @Input()
    public pageNumber: number;
    @Input()
    public pageSize: number;
    @Input()
    public totalPages: number;

    @Output()
    public loadUsersEvent:EventEmitter<LazyLoadEvent> = new EventEmitter();
    @Output()
    public selectedCustomer: CustomerResource = new CustomerResource();

    public emptyListMessage: string;

    constructor(_translateService: TranslateService) {
        _translateService.get("GENERIC_NO-RECORDS-FOUND")
            .subscribe(value => this.emptyListMessage = value);
    }

    public ngOnInit() {
        console.log("PageNumber:" + this.pageNumber)
        console.log("PageSize:" + this.pageSize)
        console.log("TotalPages:" + this.totalPages)
    }

    public loadCustomersLazy(event: LazyLoadEvent) {
        console.log(event.first);
        console.log(event.rows);
        this.loadUsersEvent.emit(event);
    }
}
