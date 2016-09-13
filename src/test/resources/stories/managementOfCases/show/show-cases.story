Narrative:
  In order to work on cases
  As a transport specialist
  I want to see a list of open cases
  

Scenario: List open cases without cases
Given There are no open cases
When I show the list of open cases
Then I see an empty list

Scenario: List open cases with 1 case
Given There is 1 case
When I show the list of open cases
Then I see 1 case

Scenario: Show entered name
Given the main page is shown
When I enter the name John Doe
Then John Doe should be greeted