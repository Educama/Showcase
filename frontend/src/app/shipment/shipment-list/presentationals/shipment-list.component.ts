import {Component, Input, Output, EventEmitter, OnInit, OnChanges} from "@angular/core";
import {ShipmentResource} from "../../shipment-common/api/resources/shipment.resource";
import {TranslateService} from "@ngx-translate/core";
import {SelectItem} from "primeng/primeng";

interface City {
  name: string;
  code: string;
}

@Component({
  selector: "educama-shipment-list",
  templateUrl: "./shipment-list.component.html"
})
export class ShipmentListComponent implements OnChanges {

  @Input()
  public shipmentList: ShipmentResource[];

  @Output()
  public selectedShipment: ShipmentResource = new ShipmentResource();

  @Output()
  public shipmentSelectedEvent: EventEmitter<ShipmentResource> = new EventEmitter();

  public emptyListMessage: string;

  statuses: SelectItem[] = [];


  constructor(public _translateService: TranslateService) {
    _translateService.get("GENERIC_NO-RECORDS-FOUND")
      .subscribe(value => this.emptyListMessage = value);
  }


  public ngOnChanges() {
    const selectItems: SelectItem[] = [];

    this.shipmentList.map(shipment => {
        if (!selectItems.find(val => val.value === shipment.status)) {
          this._translateService.get(shipment.status)
            .subscribe(value =>
              selectItems.push(<SelectItem>{label: value, value: shipment.status})
            );
        }
      }
    );
    this.statuses = selectItems;
  }

  onRowSelect(event: Event) {
    this.shipmentSelectedEvent.emit(this.selectedShipment);
  }

}
