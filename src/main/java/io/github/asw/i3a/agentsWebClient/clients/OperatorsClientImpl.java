package io.github.asw.i3a.agentsWebClient.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.asw.i3a.agentsWebClient.types.Operator;

@Service
public class OperatorsClientImpl implements OperatorsClient {

	@Value("${operators.url}")
    private String service_url;
	
	@Override
	public Operator[] findAll() {
		return new RestTemplate().getForObject( service_url + "/operators", Operator[].class );
	}
}
