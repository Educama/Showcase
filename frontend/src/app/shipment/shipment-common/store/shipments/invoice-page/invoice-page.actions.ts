import {Action} from "@ngrx/store";
import {InvoiceResource} from "../../../api/resources/invoice.resource";

// Invoice API actions
export const CREATE_INVOICE_ACTION = "CREATE_INVOICE_ACTION";
export  const CREATE_INVOICE_SUCCESSFUL_ACTION = "CREATE_INVOICE_SUCCESSFUL_ACTION";

export class CreateInvoiceAction implements Action {
  type = CREATE_INVOICE_ACTION;

  constructor(public trackingID: string, public payload: InvoiceResource) {
  }
}

export class CreateInvoiceSuccessfulAction implements Action {
  type = CREATE_INVOICE_SUCCESSFUL_ACTION;

  constructor(public payload: InvoiceResource) {
  }
}


