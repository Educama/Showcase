import {
  Component,
  DoCheck,
  OnDestroy,
  OnInit
} from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators
} from "@angular/forms";
import { Address } from "../../customer-common/api/datastructures/address.datastructure";
import { CustomerResource } from "../../customer-common/api/resources/customer.resource";
import { EditorMode } from "../../../shared/ui/enums/editor-mode.enum";
import { Subscription } from "rxjs";
import { Store } from "@ngrx/store";
import { State } from "../../../app.reducers";
import { SaveCustomerAction } from "../../customer-common/store/customer-capture-page.actions";
import { Router } from "@angular/router";
import { CustomerCaptureSlice } from "../../customer-common/store/customer-capture-page.slice";

@Component({
  selector: "educama-customer-capture",
  templateUrl: "./customer-capture.component.html",
  styleUrls: [ "./customer-capture.component.scss" ]
})
export class CustomerCaptureComponent implements OnInit, DoCheck, OnDestroy {

  // relevant slice of store and subscription for this slice
  public customerCaptureSliceSubscription: Subscription;

  public customerCaptureForm: FormGroup;
  public editorMode: EditorMode;

  public customer: CustomerResource;
  public saving = false;

  private _isInitialized = false;

  constructor(private _formBuilder: FormBuilder,
              private _router: Router,
              private _store: Store<State>) {

    this.customerCaptureSliceSubscription = this._store
                                                .select(state => state.customerCaptureSlice)
                                                .subscribe(customerCaptureSlice => this.updateModel(customerCaptureSlice));
  }

  public ngOnInit() {

    this.customerCaptureForm = this._formBuilder.group({
      uuid: [
        "",
        []
      ],
      name: [
        "",
        [ Validators.required ]
      ],
      street: [
        "",
        [ Validators.required ]
      ],
      housenumber: [
        "",
        [ Validators.required ]
      ],
      postalcode: [
        "",
        [ Validators.required ]
      ],
      city: [
        "",
        [ Validators.required ]
      ]
    });

    // Disable the uuid form field
    this.customerCaptureForm.get("uuid").disable({ onlySelf: true });

  }

  public ngDoCheck() {

    // Determine editor mode based on existence of a passed in customer
    this.editorMode = this.customer ? EditorMode.update : EditorMode.create;

    if (this.editorMode === EditorMode.update && !this._isInitialized) {
      this.customerCaptureForm.get("uuid").setValue(this.customer.uuid);
      this.customerCaptureForm.get("name").setValue(this.customer.name);
      this.customerCaptureForm.get("street").setValue(this.customer.address.street);
      this.customerCaptureForm.get("housenumber").setValue(this.customer.address.streetNo);
      this.customerCaptureForm.get("postalcode").setValue(this.customer.address.zipCode);
      this.customerCaptureForm.get("city").setValue(this.customer.address.city);
      this._isInitialized = true;
    }
  }

  public ngOnDestroy() {
    this.customerCaptureSliceSubscription.unsubscribe();
  }

  // ***************************************************
  // Event Handler
  // ***************************************************

  public cancel() {
    this._router.navigate([ "/customers" ]);
  }

  public saveCustomer() {
    const customer = new CustomerResource();
    customer.uuid = this.customerCaptureForm.get("uuid").value;
    customer.name = this.customerCaptureForm.get("name").value,
      customer.address = new Address(
        this.customerCaptureForm.get("street").value,
        this.customerCaptureForm.get("housenumber").value,
        this.customerCaptureForm.get("postalcode").value,
        this.customerCaptureForm.get("city").value);
    this._store.dispatch(new SaveCustomerAction(customer));
  }

  public updateModel(customerCaptureSlice: CustomerCaptureSlice): void {
    this.customer = customerCaptureSlice.customer;
    this.saving = customerCaptureSlice.saving;
  }

}
