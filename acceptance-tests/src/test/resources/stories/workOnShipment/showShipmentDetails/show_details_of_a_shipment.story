Narrative:
  In order to quickly assess the status of a shipment and to initiate further actions
  As a transport specialist
  I want to see the relevant details of a shipment on one screen
  

Scenario: Show the details of a shipment
Given Tom reviews the list of current shipments
When Tom selects the shipment for customer MyStuff
Then Tom sees the shipment details
And Tom sees the available tasks for this shipment
And Tom sees the active tasks for this shipment
And Tom sees the completed tasks for this shipment

