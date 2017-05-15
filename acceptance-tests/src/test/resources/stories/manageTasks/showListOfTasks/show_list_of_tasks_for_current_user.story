Narrative:
  In order to work on own tasks
  As a transport specialist
  I want to see a list of all tasks assigned to me
  

Scenario: List all tasks assigned to current user
!-- Given there are 3 tasks with name 'Check shipment' assigned to Tom
!-- And there are 2 tasks with name 'Check shipment' assigned to Jerry
!-- When Tom requests the list of tasks
!-- Then Tom sees 3 tasks with name 'Check shipment'

Scenario: Display a message if there is no task assigned to current user
!-- Given there are 2 tasks with name 'Check shipment' assigned to Jerry
!-- When Tom requests the list of tasks
!-- Then Tom sees an empty list
