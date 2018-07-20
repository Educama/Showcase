import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import {
  ActivatedRoute,
  Router
} from "@angular/router";
import {
  Observable,
  Subscription
} from "rxjs";
import { State } from "../../../../app.reducers";
import { Store } from "@ngrx/store";
import {
  isNullOrUndefined,
  isUndefined
} from "util";
import {
  FormBuilder,
  FormControl,
  FormGroup
} from "@angular/forms";
import { CaseUiCenterAreaSlice } from "../../../shipment-common/store/shipments/case-ui-center-area-page/case-ui-center-area-page.slice";
import {
  ClearCaseUiCenterAreaSlice,
  LoadActiveTasksForCaseUiCenterAreaAction,
  LoadCompletedTasksForCaseUiCenterAreaAction,
  LoadEnabledTasksForCaseUiCenterAreaAction,
  LoadInvoiceForCaseUiCenterAreaAction,
  LoadShipmentForCaseUiCenterAreaAction
} from "../../../shipment-common/store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";
import { CaseUiCenterAreaModel } from "./case-ui-center-area.model";

@Component({
  selector: "educama-caseui-center-area",
  templateUrl: "./case-ui-center-area.component.html"
})
export class CaseUiCenterAreaComponent implements OnInit, OnDestroy {

  public selectedTask: string;
  public selectedTabIndex = 0;
  public customerTypeEnum: any;

  public displayShipmentForm: FormGroup;
  public displayFlightForm: FormGroup;
  public displayInvoiceForm: FormGroup;

  public caseUiCenterAreaSubscription: Subscription;

  // relevant slice of store and subscription for this slice
  public caseUiCenterAreaSlice: Observable<CaseUiCenterAreaSlice>;

  public invoiceIsRequested = false;

  public caseUiCenterAreaModel = new CaseUiCenterAreaModel();

  constructor(private _router: Router,
              private _store: Store<State>,
              private  _activatedRoute: ActivatedRoute,
              private _formBuilder: FormBuilder) {

    _router.events.subscribe((event) => {
      this.evaluateUrl(event.toString());
    });

    this._activatedRoute.url.subscribe(url => {
      this._store.dispatch(new LoadShipmentForCaseUiCenterAreaAction(url[ 1 ].toString()));
      this._store.dispatch(new LoadActiveTasksForCaseUiCenterAreaAction(url[ 1 ].toString()));
      this._store.dispatch(new LoadEnabledTasksForCaseUiCenterAreaAction(url[ 1 ].toString()));
      this._store.dispatch(new LoadCompletedTasksForCaseUiCenterAreaAction(url[ 1 ].toString()));

    });

    this.caseUiCenterAreaSlice = this._store.select(state => state.caseUiCenterAreaSlice);
    this.caseUiCenterAreaSubscription = this.caseUiCenterAreaSlice.subscribe(
      invoiceListSlice => {
        return this.updateCaseUiCenterAreaSlice(invoiceListSlice);
      }
    );

  }

  public ngOnInit() {
    this.initalizeShipmentForm();
    this.initalizeFlightForm();
    this.initalizeInvoiceForm();
  }

  public ngOnDestroy() {
    this._store.dispatch(new ClearCaseUiCenterAreaSlice());
    this.caseUiCenterAreaSubscription.unsubscribe();

  }

  private evaluateUrl(evevntString: string) {
    if (evevntString.includes("changeShipment")) {
      this.selectedTask = "changeShipment";
      this.selectedTabIndex = 0;
    } else if (evevntString.includes("completeShipment")) {
      this.selectedTask = "completeShipment";
      this.selectedTabIndex = 0;
    } else if (evevntString.includes("organizeFlight")) {
      this.selectedTask = "organizeFlight";
      this.selectedTabIndex = 1;
    } else if (evevntString.includes("createInvoice")) {
      this.selectedTask = "createInvoice";
      this.selectedTabIndex = 2;
    } else {
      this.selectedTask = "none";
    }
  }

  private evaluateCompletedTask() {
    if (!isNullOrUndefined(this.caseUiCenterAreaModel.completedTaskList)) {
      const completedTaskList = this.caseUiCenterAreaModel.completedTaskList.tasks;

      if (!isUndefined(completedTaskList.find(task => task.name === "Create invoice"))) {
        if (this.invoiceIsRequested === false) {
          this._store.dispatch(new LoadInvoiceForCaseUiCenterAreaAction(this.caseUiCenterAreaModel.shipment.trackingId));
          this.invoiceIsRequested = true;
        }
      }
    }
  }

  // ***************************************************
  // Data Retrieval
  // ***************************************************

  private CheckModelAndFillShipmentForm() {

    if (isNullOrUndefined(this.caseUiCenterAreaModel.shipment)) {
    } else {
      this.displayShipmentForm.get("cargoDescription").setValue(this.caseUiCenterAreaModel.shipment.shipmentCargo.cargoDescription);
      this.displayShipmentForm.get("flight").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.flight);
      this.displayShipmentForm.get("numberPackages").setValue(this.caseUiCenterAreaModel.shipment.shipmentCargo.numberPackages);
      this.displayShipmentForm.get("totalWeight").setValue(this.caseUiCenterAreaModel.shipment.shipmentCargo.totalWeight);
      this.displayShipmentForm.get("totalCapacity").setValue(this.caseUiCenterAreaModel.shipment.shipmentCargo.totalCapacity);
      this.displayShipmentForm.get("dangerousGoods").setValue(this.caseUiCenterAreaModel.shipment.shipmentCargo.dangerousGoods);
      this.displayShipmentForm.get("preCarriage").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.preCarriage);
      this.displayShipmentForm.get("exportInsurance").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.exportInsurance);
      this.displayShipmentForm.get("exportCustomsClearance").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.exportCustomsClearance);
      this.displayShipmentForm.get("importInsurance").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.importInsurance);
      this.displayShipmentForm.get("importCustomsClearance").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.importCustomsClearance);
      this.displayShipmentForm.get("onCarriage").setValue(this.caseUiCenterAreaModel.shipment.shipmentServices.onCarriage);
      this.displayShipmentForm.get("customerTypeEnum").setValue(this.caseUiCenterAreaModel.shipment.customerTypeEnum);
      this.displayShipmentForm.get("senderName").setValue(this.caseUiCenterAreaModel.shipment.sender.name);
      this.displayShipmentForm.get("senderStreet").setValue(this.caseUiCenterAreaModel.shipment.sender.address.street);
      this.displayShipmentForm.get("senderStreetNo").setValue(this.caseUiCenterAreaModel.shipment.sender.address.streetNo);
      this.displayShipmentForm.get("senderCity").setValue(this.caseUiCenterAreaModel.shipment.sender.address.city);
      this.displayShipmentForm.get("senderZipCode").setValue(this.caseUiCenterAreaModel.shipment.sender.address.zipCode);
      this.displayShipmentForm.get("receiverName").setValue(this.caseUiCenterAreaModel.shipment.receiver.name);
      this.displayShipmentForm.get("receiverStreet").setValue(this.caseUiCenterAreaModel.shipment.receiver.address.street);
      this.displayShipmentForm.get("receiverStreetNo").setValue(this.caseUiCenterAreaModel.shipment.receiver.address.streetNo);
      this.displayShipmentForm.get("receiverCity").setValue(this.caseUiCenterAreaModel.shipment.receiver.address.city);
      this.displayShipmentForm.get("receiverZipCode").setValue(this.caseUiCenterAreaModel.shipment.receiver.address.zipCode);

      this.customerTypeEnum = this.caseUiCenterAreaModel.shipment.customerTypeEnum;
    }
  }

  private checkModelAndFillFlightForm() {
    if (!isNullOrUndefined(this.caseUiCenterAreaModel.shipment)) {
      if (!isNullOrUndefined(this.caseUiCenterAreaModel.shipment.shipmentFlight.price)) {
        this.displayFlightForm.get("flightNumber").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.flightNumber);

        this.displayFlightForm.get("airline").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.airline);
        this.displayFlightForm.get("departureAirport").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.departureAirport);
        this.displayFlightForm.get("departureTime").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.departureTime.split("T")[ 1 ].replace("Z", ""));
        this.displayFlightForm.get("departureDate").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.departureTime.split("T")[ 0 ]);
        this.displayFlightForm.get("destinationAirport").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.destinationAirport);
        this.displayFlightForm.get("destinationTime")
            .setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.destinationTime
                          .split("T")[ 1 ].replace("Z", "")
            );
        this.displayFlightForm.get("destinationDate")
            .setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.destinationTime
                          .split("T")[ 0 ]
            );
        this.displayFlightForm.get("price").setValue(this.caseUiCenterAreaModel.shipment.shipmentFlight.price);
      }
    }
  }

  private checkModelAndFillInvoiceForm() {
    if (!isNullOrUndefined(this.caseUiCenterAreaModel.invoice)) {
      this.displayInvoiceForm.get("invoiceCreationDate").setValue(this.caseUiCenterAreaModel.invoice.invoiceCreationDate);
      this.displayInvoiceForm.get("preCarriage").setValue(this.caseUiCenterAreaModel.invoice.preCarriage);
      this.displayInvoiceForm.get("exportInsurance").setValue(this.caseUiCenterAreaModel.invoice.exportInsurance);
      this.displayInvoiceForm.get("exportCustomsClearance").setValue(this.caseUiCenterAreaModel.invoice.exportCustomsClearance);
      this.displayInvoiceForm.get("flightPrice").setValue(this.caseUiCenterAreaModel.invoice.flightPrice);
      this.displayInvoiceForm.get("importInsurance").setValue(this.caseUiCenterAreaModel.invoice.importInsurance);
      this.displayInvoiceForm.get("importCustomsClearance").setValue(this.caseUiCenterAreaModel.invoice.importCustomsClearance);
      this.displayInvoiceForm.get("onCarriage").setValue(this.caseUiCenterAreaModel.invoice.onCarriage);
      this.displayInvoiceForm.get("managementFee").setValue(this.caseUiCenterAreaModel.invoice.managementFee);
      this.displayInvoiceForm.get("serviceFee").setValue(this.caseUiCenterAreaModel.invoice.serviceFee);
      this.displayInvoiceForm.get("discount").setValue(this.caseUiCenterAreaModel.invoice.discount);
    }
  }

  private updateCaseUiCenterAreaSlice(centerAreaSlice: CaseUiCenterAreaSlice) {
    this.caseUiCenterAreaModel.shipment = centerAreaSlice.shipment;
    this.caseUiCenterAreaModel.invoice = centerAreaSlice.invoice;
    this.caseUiCenterAreaModel.completedTaskList = centerAreaSlice.completedTaskList;
    this.caseUiCenterAreaModel.enabledTaskList = centerAreaSlice.enabledTaskList;
    this.caseUiCenterAreaModel.activeTaskList = centerAreaSlice.activeTaskList;

    this.checkModelAndFillFlightForm();
    this.checkModelAndFillInvoiceForm();
    this.CheckModelAndFillShipmentForm();
    if (isNullOrUndefined(this.caseUiCenterAreaModel.completedTaskList) === false) {
      this.evaluateCompletedTask();
    }
  }

  private initalizeShipmentForm() {
    this.displayShipmentForm = this._formBuilder.group({
      senderName: new FormControl({ value: "", disabled: true }),
      senderStreet: new FormControl({ value: "", disabled: true }),
      senderStreetNo: new FormControl({ value: "", disabled: true }),
      senderCity: new FormControl({ value: "", disabled: true }),
      senderZipCode: new FormControl({ value: "", disabled: true }),

      receiverName: new FormControl({ value: "", disabled: true }),
      receiverStreet: new FormControl({ value: "", disabled: true }),
      receiverStreetNo: new FormControl({ value: "", disabled: true }),
      receiverCity: new FormControl({ value: "", disabled: true }),
      receiverZipCode: new FormControl({ value: "", disabled: true }),

      numberPackages: new FormControl({ value: "", disabled: true }),
      totalWeight: new FormControl({ value: "", disabled: true }),
      totalCapacity: new FormControl({ value: "", disabled: true }),
      cargoDescription: new FormControl({ value: "", disabled: true }),
      dangerousGoods: new FormControl({ value: "", disabled: true }),
      preCarriage: new FormControl({ value: "", disabled: true }),
      exportInsurance: new FormControl({ value: "", disabled: true }),
      exportCustomsClearance: new FormControl({ value: "", disabled: true }),
      flight: new FormControl({ value: "", disabled: true }),
      importInsurance: new FormControl({ value: "", disabled: true }),
      importCustomsClearance: new FormControl({ value: "", disabled: true }),
      onCarriage: new FormControl({ value: "", disabled: true }),
      customerTypeEnum: new FormControl({ value: "", disabled: true })
    });
  }

  private initalizeFlightForm() {
    this.displayFlightForm = this._formBuilder.group({
      flightNumber: new FormControl({ value: "", disabled: true }),
      airline: new FormControl({ value: "", disabled: true }),
      departureAirport: new FormControl({ value: "", disabled: true }),
      departureTime: new FormControl({ value: "", disabled: true }),
      departureDate: new FormControl({ value: "", disabled: true }),
      destinationAirport: new FormControl({ value: "", disabled: true }),
      destinationTime: new FormControl({ value: "", disabled: true }),
      destinationDate: new FormControl({ value: "", disabled: true }),
      price: new FormControl({ value: "", disabled: true })
    });
  }

  private initalizeInvoiceForm() {
    this.displayInvoiceForm = this._formBuilder.group({
      invoiceCreationDate: new FormControl({ value: "", disabled: true }),
      preCarriage: new FormControl({ value: "", disabled: true }),
      exportInsurance: new FormControl({ value: "", disabled: true }),
      exportCustomsClearance: new FormControl({ value: "", disabled: true }),
      flightPrice: new FormControl({ value: "", disabled: true }),
      importInsurance: new FormControl({ value: "", disabled: true }),
      importCustomsClearance: new FormControl({ value: "", disabled: true }),
      onCarriage: new FormControl({ value: "", disabled: true }),
      managementFee: new FormControl({ value: "", disabled: true }),
      serviceFee: new FormControl({ value: "", disabled: true }),
      discount: new FormControl({ value: "", disabled: true })
    });
  }
}
