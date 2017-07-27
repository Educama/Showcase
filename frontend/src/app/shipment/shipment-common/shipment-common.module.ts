import {NgModule} from "@angular/core";
import {SharedModule} from "../../common/shared.module";
import {ShipmentService} from "./api/shipment.service";
import {TaskService} from "./api/task.service";
import {SHIPMENT_ROUTING} from "./routes/shipment.routes";

@NgModule({
  imports: [
    SHIPMENT_ROUTING,
    SharedModule,
  ],
  providers: [
    ShipmentService,
    TaskService
  ]
})
export class ShipmentCommonModule {
}
