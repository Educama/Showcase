Narrative:
  In order to work on cases
  As a transport specialist
  I want to see a list of open cases
  

Scenario: List open cases with 5 cases
Given There are 5 cases
When I show the list of open cases
Then I see 5 cases

Scenario: List open cases without cases
Given There are no open cases
When I show the list of open cases
Then I see an empty list

Scenario: List open cases with 1 case
Given There is 1 case
When I show the list of open cases
Then I see 1 case




