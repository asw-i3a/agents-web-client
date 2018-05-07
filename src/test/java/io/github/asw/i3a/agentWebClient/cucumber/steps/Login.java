package io.github.asw.i3a.agentWebClient.cucumber.steps;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.asw.i3a.agentWebClient.cucumber.CucumberTest;
import io.github.asw.i3a.agentsWebClient.clients.AgentsClient;

public class Login extends CucumberTest{

	@Autowired
	AgentsClient agents;
	
	String username;
	String password;
	int kind;
	String agentID;
	
	@Given("^a agent username$")
	public void a_agent_username() throws Throwable {
	   username = "45170000A";
	}
	
	@Given("^an agent password$")
	public void an_agent_password() throws Throwable {
	    password = "4[[j[frVCUMJ>hU";
	}
	
	@Given("^an agent kind$")
	public void an_agent_kind() throws Throwable {
	    kind = 1; 
	}
	
	@When("^the username/password combination is correct$")
	public void the_username_password_combination_is_correct() throws Throwable {
	   agentID = agents.auth(username, password, kind);
	}
	
	@Then("^the id of the agent is returned$")
	public void the_id_of_the_agent_is_returned() throws Throwable {
		assertTrue( agentID != null );
	}

}
