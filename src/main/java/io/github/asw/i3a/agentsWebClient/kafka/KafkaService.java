package io.github.asw.i3a.agentsWebClient.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.ManagedBean;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import io.github.asw.i3a.agentsWebClient.clients.IncidentsClient;
import io.github.asw.i3a.agentsWebClient.types.Incident;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ManagedBean
@EnableAsync
public class KafkaService implements IKafkaService {

	@Value("${kafka.topic}")
	private String TOPIC;
	@Value("${incidence.defaults.state}")
	private String state;

	@Value("#{'${incidence.dangerous.tags}'.split(',')}")
	List<String> dangerousTags;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private IncidentsClient incidentsClient;

	public boolean sendIncidence( Incident incident, String kind ) {
		Map<String, Object> payload = new HashMap<String, Object>();

		payload.put( "incidentId", incidentsClient.saveIncident( incident ) );

		if (kind.equals( "SENSOR" )) {
			for (String tag : incident.getTags()) {
				if (dangerousTags.contains( tag )) {
					return send( TOPIC, new JSONObject( payload ).toString() );
				}
			}
		}
		return send( TOPIC, new JSONObject( payload ).toString() );

	}

	private boolean send( String topic, String data ) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send( topic, data );
		future.addCallback( new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess( SendResult<String, String> result ) {
				log.info( "SUCCESS on sending message \"" + data + "\" to topic " + topic );
			}

			@Override
			public void onFailure( Throwable ex ) {
				log.error( "ERROR on sending message \"" + data + "\", stacktrace "
						+ ex.getMessage() );
			}
		} );

		try {
			SendResult<String, String> result = future.get();
			future.completable().complete( result );
			if (future.completable().isCompletedExceptionally()) {
				return false;
			}
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}

		return !future.isCancelled() && future.isDone();
	}
}
