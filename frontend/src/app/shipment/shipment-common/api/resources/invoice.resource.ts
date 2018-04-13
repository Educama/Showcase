export class InvoiceResource {


  constructor(trackingId: string, invoiceCreationDate: string,
              preCarriage: number, exportInsurance: number, exportCustomsClearance: number,
              flightPrice: number, importInsurance: number, importCustomsClearance: number,
              onCarriage: number, managementFee: number, serviceFee: number, discount: number) {
    this.trackingId = trackingId;
    this.invoiceCreationDate = invoiceCreationDate;
    this.preCarriage = preCarriage;
    this.exportInsurance = exportInsurance;
    this.exportCustomsClearance = exportCustomsClearance;
    this.flightPrice = flightPrice;
    this.importInsurance = importInsurance;
    this.importCustomsClearance = importCustomsClearance;
    this.onCarriage = onCarriage;
    this.managementFee = managementFee;
    this.serviceFee = serviceFee;
    this.discount = discount;
  }

  public trackingId: string;
  public invoiceCreationDate: string;
  public preCarriage: number;
  public exportInsurance: number;
  public exportCustomsClearance: number;
  public flightPrice: number;
  public importInsurance: number;
  public importCustomsClearance: number;
  public onCarriage: number;
  public managementFee: number;
  public serviceFee: number;
  public discount: number;
}
