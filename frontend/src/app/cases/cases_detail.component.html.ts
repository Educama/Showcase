export const HTML_TEMPLATE = `
    <control-panel-detail [id]="instance.id"></control-panel-detail>
    <table class="table">
        <tr>
            <th>Case ID:</th>
            <td>{{ instance.id }}</td>
        </tr>
         <tr>
            <th>Tracking ID:</th>
            <td>{{ instance.trackingId }}</td>
        </tr>
        <tr>
            <th>Customer:</th>
            <td>{{ instance.customer }}</td>
        </tr>
        <tr>
            <th>Pick-up Address:</th>
            <td>{{ instance.pickup_address }}</td>
        </tr>
         <tr>
            <th>Delivery Address:</th>
            <td>{{ instance.delivery_address }}</td>
        </tr>
    </table>
`;