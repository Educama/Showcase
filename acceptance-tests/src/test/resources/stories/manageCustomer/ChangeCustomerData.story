Narrative:
In order that customer data has been changed
As a transport specialist
I want to manage the customer data

Scenario: Abort changing customer data
Given There is a customer named Apple 
When I change the name Apple to Samsung and abort the action
Then There arent any changes in the system

Scenario: Change customer data
Given There is a customer named Apple
When I change the name Apple to Samsung and save the data
Then The name of the customer is Samsung
