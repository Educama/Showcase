import {Component, EventEmitter, OnDestroy, OnInit, Output} from "@angular/core";
import {InvoiceResource} from "../../shipment-common/api/resources/invoice.resource";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: "educama-shipment-invoice",
  templateUrl: "./shipment-invoice.component.html"
})
export class ShipmentInvoiceComponent implements OnInit {
  private trackingId: string;
  public invoiceCreationDate: string;
  public preCarriage: any;
  public exportInsurance: any;
  public exportCustomsClearance: any;
  public flightPrice: any;
  public importInsurance: any;
  public importCustomsClearance: any;
  public onCarriage: any;
  public managementFee: any;
  public serviceFee: any;
  public discount: any;


  @Output()
  public createInvoiceEvent: EventEmitter<InvoiceResource> = new EventEmitter();

  @Output()
  public cancelInvoiceEvent: EventEmitter<string> = new EventEmitter();

  constructor(private _activatedRoute: ActivatedRoute) {
  }

  public ngOnInit() {
    this._activatedRoute.parent.params.subscribe(params => {
      this.trackingId = params["id"];
    });
  }


  public saveInvoice() {
    this.createInvoiceEvent.emit(
      new InvoiceResource(
        this.trackingId, this.invoiceCreationDate, this.preCarriage, this.exportInsurance, this.exportCustomsClearance,
        this.flightPrice, this.importInsurance, this.importCustomsClearance, this.onCarriage, this.managementFee, this.serviceFee,
        this.discount
      ));

  }

  public cancleInvoice() {
    this.cancelInvoiceEvent.emit(this.trackingId);
  }
}



