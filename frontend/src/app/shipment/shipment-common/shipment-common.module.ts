import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {ShipmentService} from "./api/shipment.service";
import {TaskService} from "./api/task.service";
import {SHIPMENT_ROUTING} from "./routes/shipment.routes";
import {EffectsModule} from "@ngrx/effects";
import {ShipmentListEffect} from "./effects/shipment-list.effect";
import {TaskListEffect} from "./effects/task-list.effect";

@NgModule({
  imports: [
    EffectsModule.forFeature([
      ShipmentListEffect,
      TaskListEffect
    ]),
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
