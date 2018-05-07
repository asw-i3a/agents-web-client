package io.github.asw.i3a.agentWebClient.cucumber.steps;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.asw.i3a.agentWebClient.cucumber.CucumberTest;
import io.github.asw.i3a.agentsWebClient.clients.IncidentsClient;
import io.github.asw.i3a.agentsWebClient.types.Incident;

public class InputIncident extends CucumberTest{

	Incident i;
	String incidentID;
	
	@Autowired
	IncidentsClient incidents;
	
	@Given("^data for the incident$")
	public void data_for_the_incident() throws Throwable {
		i = new Incident();
		
		String username = "Juan";
		String password = "mostsecureis123";
		String incidenceName = "test1";
		String description = "testing the format of json";
		String asignee = "Pablo";
		String state = "OPEN";
		Long expiration = 9231312L;
		String location = "12N30W";
		String kind = "SENSOR";
		
		
		ArrayList<String> tags = new ArrayList<String>();
		tags.add( "IMPORTANTE" );
		tags.add( "PELIGROSO" );

		ArrayList<String> additionalInfo = new ArrayList<String>();
		additionalInfo.add( "bin/img1.png" );
		additionalInfo.add( "fragile" );

		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put( "prioridad", "alta" );
		
		i.setTitle( incidenceName );
		i.setDescription( description );
		i.setOperatorId( asignee );
		i.setStatus( state );
		i.setTags( tags.toArray( new String[0] ) );
		i.setMultimedia( additionalInfo.toArray( new String[0] ) );
		i.setLocation( location );
		i.setPropertyVal( properties );
	}

	
	@Given("^data for the incident with dangerous tag$")
	public void data_for_the_incident_with_dangeroues_tag() throws Throwable {
		i = new Incident();
		
		String username = "Juan";
		String password = "mostsecureis123";
		String incidenceName = "test1";
		String description = "testing the format of json";
		String asignee = "Pablo";
		String state = "OPEN";
		Long expiration = 9231312L;
		String location = "12N30W";

		ArrayList<String> tags = new ArrayList<String>();
		tags.add( "DANGEROUES" );

		ArrayList<String> additionalInfo = new ArrayList<String>();
		additionalInfo.add( "bin/img1.png" );
		additionalInfo.add( "fragile" );

		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put( "prioridad", "alta" );
		
		i.setTitle( incidenceName );
		i.setDescription( description );
		i.setOperatorId( asignee );
		i.setStatus( state );
		i.setTags( tags.toArray( new String[0] ) );
		i.setMultimedia( additionalInfo.toArray( new String[0] ) );
		i.setLocation( location );
		i.setPropertyVal( properties );
	}
	
	@When("^the incident is sent$")
	public void the_incident_is_sent() throws Throwable {
	   incidentID = incidents.saveIncident(i);
	   i.setIncidentId(incidentID);
	}

	@Then("^the id assigned to the incident is returned$")
	public void the_id_assigned_to_the_incident_is_returned() throws Throwable {
		assertTrue( i.getIncidentId() != null );
	}
}
