Narrative:
  In order to find a specific case for a customers (e.g. when the customer is calling)
  As a transport specialist
  I want to quickly identify the case concerning the customer
  

Scenario: Search for cases by exact customer name
Given There are 5 cases, one from customer MyStuff, one from customer MySofa and 3 from customer Stuff24
When I show the list of open cases
And I enter "MyStuff" into the search input field
Then I see 1 case from customer MyStuff

Scenario: Search for cases by substrings of customer name
Given There are 5 cases, one from customer MyStuff, one from customer MySofa and 3 from customer Stuff24
When I show the list of open cases
And I enter "ys" into the search input field
Then I see 2 cases, one from customer MyStuff and one from customer MySofa

Scenario: Display a message if the search string does not match any case
Given There are 5 cases, one from customer MyStuff, one from customer MySofa and 3 from customer Stuff24
When I show the list of open cases
And I enter "abc" into the search input field
Then I see a message "no case found for 'abc'"




