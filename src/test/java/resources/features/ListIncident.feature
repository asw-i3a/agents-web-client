Feature: List incidents

	Scenario: List the incidents created by an agent
		Given a agentID
		Then the list of incidents created by that agent is returned