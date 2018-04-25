package org.uniovi.i3a.incimanager.clients;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uniovi.i3a.incimanager.types.Incident;

public class IncidentsServiceClientImpl implements IncidentsServiceClient {

    @Override
    public Incident[] findByAgentId(String agentId) {
	
	UriComponentsBuilder url = UriComponentsBuilder.fromUriString("http://asw-i3a-zuul-eu-west-1.guill.io/incidents_service/incidents");
	url.queryParam("agentId", agentId);
	
	return new RestTemplate().getForObject(url.toUriString(), Incident[].class);
    }

}
