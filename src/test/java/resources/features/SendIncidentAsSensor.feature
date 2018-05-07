Feature: Send incident as sensor

	Scenario: An agent log into the system
		Given a agent username
		And an agent password
		And an agent kind
		When the username/password combination is correct
		Then the id of the agent is returned
		
	Scenario: An operator input data for the incident
		Given data for the incident
		When the incident is sent
		Then the id assigned to the incident is returned