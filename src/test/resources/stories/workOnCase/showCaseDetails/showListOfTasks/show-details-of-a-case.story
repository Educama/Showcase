Narrative:
  In order to quickly assess the status of a case and to initiate further actions
  As a transport specialist
  I want to see the relevant details of a case on one screen
  

Scenario: Show the details of a case
Given Tom reviews the list of open cases
When Tom selects the case for customer MyStuff
Then Tom sees the case details
And Tom sees the available tasks for this case
And Tom sees the active tasks for this case
And Tom sees the completed tasks for this case

