import {Component, OnDestroy} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {InvoicePageSlice} from "../../shipment-common/store/shipments/invoice-page/invoice-page.slice";
import {Subscription} from "rxjs/Subscription";
import {ShipmentInvoicePageModel} from "./shipment-invoice-page.model";
import {Store} from "@ngrx/store";
import {State} from "../../../app.reducers";
import {ActivatedRoute, Router} from "@angular/router";
import {InvoiceResource} from "../../shipment-common/api/resources/invoice.resource";
import {CreateInvoiceAction} from "../../shipment-common/store/shipments/invoice-page/invoice-page.actions";
import {ReloadStoreAction} from "../../shipment-common/store/shipments/shipment-capture-page/shipment-capture-page.actions";

@Component({
  selector: "educama-shipment-invoice-page",
  templateUrl: "./shipment-invoice-page.component.html"
})
export class ShipmentInvoicePageComponent implements OnDestroy {

  // relevant slice of store and subscription for this slice
  public invoiceListSlice: Observable<InvoicePageSlice>;
  public invoiceListSliceSubscription: Subscription;

  // model for the page
  public enabledTaskListModel: ShipmentInvoicePageModel = new ShipmentInvoicePageModel();

  constructor(private _store: Store<State>,
              private _router: Router,
              private _activatedRoute: ActivatedRoute) {

    this.invoiceListSlice = this._store.select(state => state.invoicePageSlice);

    this.invoiceListSliceSubscription = this.invoiceListSlice
      .subscribe(invoiceListSlice => this.invoiceListSlice);
  }


  public ngOnDestroy() {
    this.invoiceListSliceSubscription.unsubscribe();
  }

  // ***************************************************
  // Event Handler
  // ***************************************************

  public onCreateInvoiceEvent(invoiceResource: InvoiceResource) {
    const trackingId = invoiceResource.trackingId;
    this._store.dispatch(new CreateInvoiceAction(trackingId, invoiceResource));
    this._router.navigate(["caseui/" + trackingId]);
    this._store.dispatch(new ReloadStoreAction(trackingId));
  }

  public onCancelInvoiceEvent(trackingId: string) {
    this._router.navigate(["caseui/" + trackingId]);
    this._store.dispatch(new ReloadStoreAction(trackingId));
  }
}
