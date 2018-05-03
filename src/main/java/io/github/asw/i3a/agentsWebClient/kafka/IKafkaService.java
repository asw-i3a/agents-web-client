package io.github.asw.i3a.agentsWebClient.kafka;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import io.github.asw.i3a.agentsWebClient.types.Incident;

@Configuration
@EnableKafka
public interface IKafkaService {

    /**
     * Sends a payload map to the Kafka stream pipe and stores in mongoDB for
     * redundancy.
     *
     * @return true if the Kafka enqueue is done. False otherwise.
     *
     * @param payload
     *            is the map of key type string and value type object that will
     *            represent the JSON of the request. The Object value type helps not
     *            to bound the value to only one type.
     */
    public boolean sendIncidence(Incident incident, String kind);

}
