package org.uniovi.i3a.incimanager.kafka;

import java.util.HashMap;
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
import org.uniovi.i3a.incimanager.clients.IncidentsServiceClient;
import org.uniovi.i3a.incimanager.clients.IncidentsServiceClientImpl;
import org.uniovi.i3a.incimanager.types.Incident;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ManagedBean
@EnableAsync
public class KafkaService implements IKafkaService {

    @Value("${kafka.topic}")
    private String TOPIC;
    @Value("${incidence.defaults.state}")
    private String state;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private IncidentsServiceClient incidentsClient = new IncidentsServiceClientImpl();

    public boolean sendIncidence(Incident incident) {
	Map<String, Object> payload = new HashMap<String, Object>();

	payload.put("incidentId", incidentsClient.saveIncident(incident));

	return send(TOPIC, new JSONObject(payload).toString());

    }

    private boolean send(String topic, String data) {
	ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
	future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
	    @Override
	    public void onSuccess(SendResult<String, String> result) {
		log.info("SUCCESS on sending message \"" + data + "\" to topic " + topic);
	    }

	    @Override
	    public void onFailure(Throwable ex) {
		log.error("ERROR on sending message \"" + data + "\", stacktrace " + ex.getMessage());
	    }
	});

	try {
	    SendResult<String, String> result = future.get();
	    future.completable().complete(result);
	    if (future.completable().isCompletedExceptionally()) {
		return false;
	    }
	} catch (InterruptedException | ExecutionException e) {
	    return false;
	}

	return !future.isCancelled() && future.isDone();
    }
}
