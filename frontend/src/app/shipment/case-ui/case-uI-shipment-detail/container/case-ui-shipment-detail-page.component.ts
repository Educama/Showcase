import {
  Component,
  OnChanges,
  OnDestroy,
  OnInit
} from "@angular/core";
import {
  Observable,
  Subscription
} from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { State } from "../../../../app.reducers";
import { Store } from "@ngrx/store";
import { CaseUIShipmentDetailModel } from "./case-ui-shipment-detail-page.model";
import { ShipmentCaptureSlice } from "../../../shipment-common/store/shipments/shipment-capture-page/shipment-capture-page.slice";
import { RequestSingleShipment } from "../../../shipment-common/store/shipments/shipment-list-page/shipment-list-page.actions";
import { ResetShipmentCaptureSliceAction } from "../../../shipment-common/store/shipments/shipment-capture-page/shipment-capture-page.actions";

@Component({
  selector: "educama-caseui-shipment-detail-page",
  templateUrl: "./case-ui-shipment-detail-page.component.html"
})
export class CaseUIShipmentDetailPageComponent implements OnInit, OnDestroy, OnChanges {

  // relevant slice of store and subscription for this slice
  public shipmentDetailSlice: Observable<ShipmentCaptureSlice>;
  public shipmentDetailSliceSubscription: Subscription;

  // model for the page
  public shipmentDetailInfoModel: CaseUIShipmentDetailModel = new CaseUIShipmentDetailModel();

  constructor(private _activatedRoute: ActivatedRoute,
              private _store: Store<State>) {

    this.shipmentDetailSlice = this._store.select(state => state.shipmentCaptureSlice);

    this.shipmentDetailSliceSubscription = this.shipmentDetailSlice.subscribe(
      shipmentCaptureSlice => this.updateShipmentCaptureModel(shipmentCaptureSlice)
    );
  }

  public ngOnInit() {
    this._activatedRoute.params.subscribe(params => {
      if (params[ "id" ] && params[ "id" ] !== "capture") {
        this._store.dispatch(new RequestSingleShipment(params[ "id" ]));
      }
      console.log(params[ "id" ]);
    });
  }

  public ngOnChanges() {
    this._activatedRoute.params.subscribe(params => {
      if (params[ "id" ] && params[ "id" ] !== "capture") {
        this._store.dispatch(new RequestSingleShipment(params[ "id" ]));
      }
      console.log(params[ "id" ]);
    });
  }

  public ngOnDestroy() {
    this._store.dispatch(new ResetShipmentCaptureSliceAction());
    this.shipmentDetailSliceSubscription.unsubscribe();
  }

  // ***************************************************
  // Event Handler
  // ***************************************************

  // ***************************************************
  // Data Retrieval
  // ***************************************************

  private updateShipmentCaptureModel(shipmentCaptureSlice: ShipmentCaptureSlice) {
    this.shipmentDetailInfoModel.shipment = shipmentCaptureSlice.shipment;
  }
}
