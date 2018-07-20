import {
  Component,
  OnDestroy,
  OnInit
} from "@angular/core";
import {
  Observable,
  Subscription
} from "rxjs";
import { AirportResource } from "../../flights-common/api/airports/airport.resource";
import { AirlineResource } from "../../flights-common/api/airlines/airline.resource";
import { OrganizeFlightFormPageModel } from "../container/organize-flight-form-page.model";
import {
  ActivatedRoute,
  Router
} from "@angular/router";
import { Store } from "@ngrx/store";
import { AirlineService } from "../../flights-common/api/airlines/airline.service";
import { AirportService } from "../../flights-common/api/airports/airport.service";
import { State } from "../../../app.reducers";
import { RequestSingleShipment } from "../../../shipment/shipment-common/store/shipments/shipment-list-page/shipment-list-page.actions";
import {
  ReloadStoreAction,
  ResetShipmentCaptureSliceAction
} from "../../../shipment/shipment-common/store/shipments/shipment-capture-page/shipment-capture-page.actions";
import { SaveFlightAction } from "../../../shipment/shipment-common/store/shipments/organize-flight-page/organize-flight-page.actions";
import { OrganizeFlightResource } from "../../../shipment/shipment-common/api/resources/organize-flight.resource";
import { OrganizeFlightSlice } from "../../../shipment/shipment-common/store/shipments/organize-flight-page/organize-flight-page.slice";
import { ReloadShipmentAndTasksForCaseUiACtion } from "../../../shipment/shipment-common/store/shipments/case-ui-center-area-page/case-ui-center-area-page.actions";

@Component({
  selector: "educama-organize-flight-form",
  templateUrl: "./organize-flight-form.component.html",
  styleUrls: [ "../organize-flight-form-style.scss" ]
})
export class OrganizeFlightFormComponent implements OnInit, OnDestroy {

  private trackingId: string;
  value: Date;
  public airlineSuggestion: any;
  public airportSuggestion: any;

  selectedAirline: AirlineResource;
  selectedStartAirport: AirportResource;
  selectedDestinationAirport: AirportResource;

  public flightNumber: string;
  public price: number;

  public departureAirport: string;
  public departureDate: string;
  public destinationAirport: string;
  public destinationDate: string;

  public shipmentDetailSlice: Observable<OrganizeFlightSlice>;
  public shipmentDetailSliceSubscription: Subscription;

  // model for the page
  public shipmentDetailInfoModel: OrganizeFlightFormPageModel = new OrganizeFlightFormPageModel();

  constructor(private _router: Router,
              private _activatedRoute: ActivatedRoute,
              private _store: Store<State>,
              private _airlineService: AirlineService,
              private _airportService: AirportService) {

    this.shipmentDetailSlice = this._store.select(state => state.organizeFlightPageSlice);

    this.shipmentDetailSliceSubscription = this.shipmentDetailSlice.subscribe(
      shipmentCaptureSlice => this.updateShipmentCaptureModel(shipmentCaptureSlice)
    );
  }

  public ngOnInit() {
    this._activatedRoute.parent.params.subscribe(params => {
      this.trackingId = params[ "id" ];
      this._store.dispatch(new RequestSingleShipment(params[ "id" ]));
    });
  }

  public ngOnDestroy() {
    this._store.dispatch(new ResetShipmentCaptureSliceAction());
    this.shipmentDetailSliceSubscription.unsubscribe();
  }

  public saveFlight() {
    this._store.dispatch(new SaveFlightAction(this.trackingId, new OrganizeFlightResource(
      this.flightNumber, this.selectedAirline.name, this.price,
      this.departureAirport, this.departureDate,
      this.destinationAirport, this.destinationDate
    )));

    this._router.navigate([ "caseui/" + this.trackingId ]);
    this._store.dispatch(new ReloadStoreAction(this.trackingId));
    this._store.dispatch(new ReloadShipmentAndTasksForCaseUiACtion(this.trackingId));
  }

  public cancleFlight() {
    this._store.dispatch(new ReloadStoreAction(this.trackingId));
    this._router.navigate([ "caseui/" + this.trackingId ]);
  }

  // ***************************************************
  // Event Handler
  // ***************************************************
  public loadAirlineSuggestions(event: any) {
    this._airlineService.findAirlineSuggestions(event.query)
        .subscribe(customerSuggestionResource => this.airlineSuggestion = customerSuggestionResource);
  }

  public loadAirportSuggestions(event: any) {
    this._airportService.findAirportSuggestions(event.query)
        .subscribe(customerSuggestionResource => this.airportSuggestion = customerSuggestionResource);
  }

  public onAirlineSelected(airline: AirlineResource) {

    this.selectedAirline = airline;
  }

  public onStartAirportSelected(airport: AirportResource) {
    this.departureAirport = airport.iataCode;
    this.selectedStartAirport = airport;
  }

  public onDestinationAirportSelected(airport: AirportResource) {
    this.destinationAirport = airport.iataCode;
    this.selectedDestinationAirport = airport;
  }

  // ***************************************************
  // Data Retrieval
  // ***************************************************

  private updateShipmentCaptureModel(shipmentCaptureSlice: OrganizeFlightSlice) {
    this.shipmentDetailInfoModel.flight = shipmentCaptureSlice.flight;
  }

}
