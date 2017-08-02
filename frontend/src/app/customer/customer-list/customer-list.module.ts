import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {CustomerListPageComponent} from "./container/customer-list-page.component";
import {CustomerListComponent} from "./components/customer-list.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    CustomerListPageComponent,
    CustomerListComponent
  ],
  exports: [
    CustomerListPageComponent
  ]
})
export class CustomerListModule {
}
