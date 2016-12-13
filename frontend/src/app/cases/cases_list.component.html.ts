export const HTML_TEMPLATE = `
    <table id="casesList" class="table">
        <tr>
            <th>Case ID</th>
            <th>Tracking ID</th>
            <th>Customer</th>
            <th>Pick-Up Address</th>
            <th>Delivery Address</th>
        </tr>
        <tr *ngFor="let case of cases" (click)="onSelect(case)" [class.highlight]="case === selectedCase">
            <td>{{ case.id }}</td>
            <td>{{ case.trackingId }}</td>
            <td>{{ case.customer }}</td>
            <td>{{ case.pickup_address }}</td>
            <td>{{ case.delivery_address }}</td>
        </tr>
    </table>
    <div *ngIf="selectedCase">
        <button class="btn btn-primary" (click)="gotoDetail()">View Details</button>
    </div>
`;