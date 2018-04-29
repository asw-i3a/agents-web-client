package org.uniovi.i3a.incimanager.clients;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uniovi.i3a.incimanager.types.Incident;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IncidentsServiceClientImpl implements IncidentsServiceClient {

    @Override
    public Incident findByIncidentId(String incidentId) {
	UriComponentsBuilder url = UriComponentsBuilder.fromUriString("http://asw-i3a-zuul-eu-west-1.guill.io/incidents_service/incidents/"+incidentId);

	return new RestTemplate().getForObject(url.toUriString(), Incident.class);
    }
    
    
    @Override
    public Incident[] findByAgentId(String agentId) {
	
	UriComponentsBuilder url = UriComponentsBuilder.fromUriString("http://asw-i3a-zuul-eu-west-1.guill.io/incidents_service/incidents");
	url.queryParam("agentId", agentId);
	
	return new RestTemplate().getForObject(url.toUriString(), Incident[].class);
    }
    
    

    @Override
    public String saveIncident(Incident incident) {	
	try {
	    JsonNode json = new JsonNode(new ObjectMapper().writeValueAsString(incident));
	    HttpResponse<JsonNode> response = Unirest.post("http://asw-i3a-zuul-eu-west-1.guill.io/incidents_service/save").header("Content-Type", "application/json; charset=utf8;")
	    .body(json).asJson();
	    if(response.getStatus() == HttpStatus.OK.value()) {
		log.info("Added the inident to the service");
		return response.getBody().getObject().getString("incidentId");
	    }
	    log.error("Not added the inident to the service");
	    return "";
	} catch (JsonProcessingException | UnirestException e) {
	    log.error("Exception while adding the incident to the service: " + e.getMessage());
	    return "";
	}
    }

}
