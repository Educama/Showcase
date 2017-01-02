export const HTML_TEMPLATE = `
    <div *ngIf="isVisible" class="modal fade show in danger" id="myModal" role="dialog">
        <div class="modal-dialog">

            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Neuer Transportauftrag</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="customer">Customer:</label>
                        <input #customer type="text" class="form-control" id="customerName">
                    </div>
                     <div class="form-group">
                        <label for="pickup_address">Pick-Up Address:</label>
                        <input #pickup_address type="text" class="form-control" id="pickup_addressName">
                    </div>
                    <div class="form-group">
                        <label for="delivery_address">Delivery Address:</label>
                        <input #delivery_address type="text" class="form-control" id="delivery_addressName">
                    </div>
                    <div [hidden]="validFieldValues" class="text-warning">
                        Alle Felder müssen gefüllt sein, um einen neuen Transportauftrag anzulegen!
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" (click)="close()">Cancel</button>
                    <button id="newCase" type="button" class="btn btn-primary" (click)="submit(type.value, customer.value)">OK</button>
                </div>
            </div>
        </div>
    </div>
`;