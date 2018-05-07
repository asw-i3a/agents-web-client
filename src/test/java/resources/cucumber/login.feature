Feature: Login as an agent
Scenario: Correct login
Given the agent is correct
When the username exists
And the password corresponds to the username
Then the agent should access the form page