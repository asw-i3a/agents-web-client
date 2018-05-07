package io.github.asw.i3a.agentWebClient.cucumber.steps;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.asw.i3a.agentWebClient.cucumber.CucumberTest;
import io.github.asw.i3a.agentsWebClient.clients.IncidentsClient;
import io.github.asw.i3a.agentsWebClient.types.Incident;

public class ListIncidents extends CucumberTest {
	
	@Autowired
	IncidentsClient incidentsCl;
	
	Incident[] incidents;
	String agentID = "45170929X";
	
	@Given("^a agentID$")
	public void a_agentID() throws Throwable {
	    incidents = incidentsCl.findByAgentId( agentID );
	}

	@Then("^the list of incidents created by that agent is returned$")
	public void the_list_of_incidents_created_by_that_agent_is_returned() throws Throwable {
	   for(Incident i : incidents)
	   {
		   assertTrue( i.getAgentId().equals(  agentID  )  );
	   }
	}
}
