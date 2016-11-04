Narrative:
  In order to work on own tasks
  As a transport specialist
  I want to see a list of all tasks assigned to me
  

Scenario: List all tasks assigned to current user
Given There are 5 tasks, 3 of them assigned to the current user
When I show the list of tasks
Then I see 3 tasks

Scenario: Display a message if there is no task assigned to current user
Given There are 5 tasks, 0 of them assigned to the current user
When I show the list of tasks
Then I see a message "no task is currently assigned to you"

