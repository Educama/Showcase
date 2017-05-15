Narrative: 
  In order to capture the details of a new shipment ordered by phone
  As a transport specialist
  I want to enter the relevant information of the shipment into the system 


Scenario: Create a new shipment based on minimal required information
Given Tom views the shipment list
When Tom opens the dialog to create a new shipment
And Tom enters Schrauben Schmitt as customer name, Echterdingen as pickup address and Tokio as delivery address
Then Tom sees the list of current shipments, including the shipment for customer Schrauben Schmitt
And a shipment has been created


Scenario: Create a new shipment with all information
Given <persona> views the shipment list
When <persona> opens the dialog to create a new shipment
And <persona> enters <customer_name>, <pickup_address> and <delivery_address>
And <persona> enters <number_of_items>, <weight>, <capacity>, declared as <goods_type> and <description> 
And <persona> selects the following <list_of_services> 
Then <persona> sees the list of current shipments, including the shipment for <customer>
And a shipment has been created

Examples:
|persona|customer_name|pickup_address|delivery_address|number_of_items|weight|capacity|goods_type|description|list_of_services|
|Tom|Schrauben Schmitt|Echterdingen|Tokio|4|1350|5,5|normal|new material|flight|
|Tom|Schrauben Schmitt|Echterdingen|Tokio|4|1350|5,5|dangerous|new material|flight|
|Tom|Schrauben Schmitt|Echterdingen|Tokio|4|1350|5,5|normal|new material|precarriage,export credit insurance,export customs clearance,flight,import customs clearance,import credit insurance,postcarriage|
|Tom|Schrauben Schmitt|Echterdingen|Tokio|4|1350|5,5|normal|new material|precarriage,flight,postcarriage|
