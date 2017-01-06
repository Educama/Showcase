import {NgModule} from "@angular/core";
import {SharedModule} from "../common/shared.module";
import {SHIPMENT_ROUTING} from "./routes/shipment.routes";
import {ShipmentService} from "./api/shipment.service";
import {ShipmentListPageComponent} from "./container/shipment-list-page.component";
import {ShipmentListComponent} from "./components/shipment-list.component";
import {ShipmentCapturePageComponent} from "./container/shipment-capture-page.component";
import {ShipmentCaptureComponent} from "./components/shipment-capture.component";
import {TabViewComponent} from "./container/tabview-page.component";

@NgModule({
    imports: [SharedModule, SHIPMENT_ROUTING],
    declarations: [ShipmentCapturePageComponent, ShipmentCaptureComponent,
        ShipmentListPageComponent, ShipmentListComponent, TabViewComponent],
    exports: [ShipmentListPageComponent, ShipmentCapturePageComponent],
    providers: [ShipmentService]
})
export class ShipmentModule {
}
