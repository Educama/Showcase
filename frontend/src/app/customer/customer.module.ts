import {NgModule} from "@angular/core";
import {SharedModule} from "../common/shared.module";
import {CUSTOMER_ROUTING} from "./routes/customer.routes";
import {CustomerService} from "./api/customer.service";
import {CustomerListPageComponent} from "./container/customer-list-page.component";
import {CustomerListComponent} from "./components/customer-list.component";
import {CustomerCapturePageComponent} from "./container/customer-capture-page.component";
import {CustomerCaptureComponent} from "./components/customer-capture.component";

@NgModule({
    imports: [SharedModule, CUSTOMER_ROUTING],
    declarations: [CustomerCapturePageComponent, CustomerCaptureComponent,
        CustomerListPageComponent, CustomerListComponent],
    exports: [CustomerListPageComponent, CustomerCapturePageComponent],
    providers: [CustomerService]
})
export class CustomerModule {
}
