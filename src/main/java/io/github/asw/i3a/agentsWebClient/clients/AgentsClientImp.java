package io.github.asw.i3a.agentsWebClient.clients;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentsClientImp implements AgentsClient {

	@Value("${agents.url}")
    private String service_url;

	@Override
	public String getAgentName( String id ) {
		try {
			HttpResponse<JsonNode> response = Unirest.post( service_url + "/agents/" + id ).asJson();
			return response.getBody().getObject().getString( "name" );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
		return "No name found";
	}

	@Override
	public String auth( String username, String password, int kind ) {
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put( "login", username );
		payload.put( "password", password );
		payload.put( "kind", kind );

		try {
			HttpResponse<JsonNode> response = Unirest.post( service_url + "/auth" )
					.header( "Content-Type", "application/json" )
					.body( new JSONObject( payload ).toString() ).asJson();
			if (response.getStatus() == HttpStatus.OK.value())
				return response.getBody().getObject().getString( "agentId" );
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
		return null;
	}
}
