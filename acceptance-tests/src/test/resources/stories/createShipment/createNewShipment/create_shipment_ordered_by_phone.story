Narrative:
  In order to capture the details of a new shipment ordered by phone
  As a transport specialist
  I want to enter the relevant information of the shipment into the system 
  

Scenario: Create a new shipment based on minimal required information
Given Tom is logged in to the application and the shipment list is displayed
When Tom opens the dialog to create a new shipment
And Tom enters Schrauben Schmitt as customer name, Echterdingen as pickup address and Tokio as delivery address
Then Tom sees the list of current shipments, including the shipment for customer Schrauben Schmitt
And a shipment id has been created 





