/*
 * This source file is part of the rest-service open source project.
 *
 * Copyright (c) 2018 willy and the rest-service project authors.
 * Licensed under GNU General Public License v3.0.
 *
 * See /LICENSE for license information.
 * 
 */
package io.github.asw.i3a.agentsWebClient.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.asw.i3a.agentsWebClient.clients.AgentsClient;
import io.github.asw.i3a.agentsWebClient.kafka.IKafkaService;
import io.github.asw.i3a.agentsWebClient.types.Comment;
import io.github.asw.i3a.agentsWebClient.types.Incident;
import lombok.extern.slf4j.Slf4j;

/**
 * Instance of RESTController.java
 * 
 * @author Guillermo Facundo Colunga
 * @version 201803152243
 */
@Slf4j
@EntityScan
@RestController
public class RESTController {

	@Autowired
	private AgentsClient agentsClient;

	@Autowired
	IKafkaService kafkaService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sensor-feed", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> processSensorRequest( @RequestBody Map<String, Object> payload ) {

		// Login

		String agentId = agentsClient.auth(
				(String) payload.get( "login" ),
				(String) payload.get( "password" ),
				(int) payload.get( "kind" ) );

		// Check result of the login

		if (agentId == null) {
			log.warn( "[ERROR] UNAUTHORIZED ACCESS: trying to access as: " + payload.get( "login" )
					+ " "
					+ payload.get( "password" ) + " " + payload.get( "kind" ) );

			return new ResponseEntity<String>(
					"{\"response\":\"UNAUTHORIZED ACCESS WILL BE REPORTED\"}",
					HttpStatus.UNAUTHORIZED );
		}

		// Parse the date in order to create an incident.

		Incident incident = new Incident();
		incident.setTitle( payload.get( "title" ).toString() );
		incident.setDescription( payload.get( "description" ).toString() );
		incident.setStatus( "OPEN" );
		incident.setLocation( payload.get( "location" ).toString() );
		System.err.println( payload.get( "tags" ) );
		System.err.println( payload.get( "tags" ).getClass() );
		String[] tags = new String[( (ArrayList<String>) payload.get( "tags" ) ).size()];
		tags = ( (ArrayList<String>) payload.get( "tags" ) ).toArray( tags );
		incident.setTags( tags );
		String[] multimedia = new String[( (ArrayList<String>) payload.get( "multimedia" ) )
				.size()];
		multimedia = ( (ArrayList<String>) payload.get( "multimedia" ) ).toArray( tags );
		incident.setMultimedia( multimedia );
		incident.setPropertyVal( (Map<String, String>) payload.get( "prop-val" ) );
		incident.setComments( new Comment[0] );
		incident.setAgentId( agentId );
		incident.setOperatorId( payload.get( "operatorId" ).toString() );

		log.info( incident.toString() );

		// Send the message to Apache Kafka | Database

		if (kafkaService.sendIncidence( incident, "SENSOR" )) {
			return new ResponseEntity<String>( "{\"response\":\"request processed\"}",
					HttpStatus.OK );
		}

		// If all went wrong return NOT_ACCEPTABLE status.
		return new ResponseEntity<String>( "{\"response\":\"request not processed\"}",
				HttpStatus.NOT_ACCEPTABLE );
	}

	@RequestMapping(value = "/info")
	public ResponseEntity<String> info() {
		Map<String, String> payload = new HashMap<String, String>();
		payload.put( "service-name", "incident-manager" );
		payload.put( "service-description", "This service allows to create incidences" );

		return new ResponseEntity<String>( new JSONObject( payload ).toString(), HttpStatus.OK );
	}
}
