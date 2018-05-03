/*
 * This source file is part of the web-service open source project.
 *
 * Copyright (c) 2018 willy and the web-service project authors.
 * Licensed under GNU General Public License v3.0.
 *
 * See /LICENSE for license information.
 * 
 */
package io.github.asw.i3a.agentsWebClient.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.asw.i3a.agentsWebClient.clients.AgentsClient;
import io.github.asw.i3a.agentsWebClient.clients.IncidentsClient;
import io.github.asw.i3a.agentsWebClient.clients.OperatorsClient;
import io.github.asw.i3a.agentsWebClient.kafka.KafkaService;
import io.github.asw.i3a.agentsWebClient.types.Comment;
import io.github.asw.i3a.agentsWebClient.types.Incident;
import lombok.extern.slf4j.Slf4j;

/**
 * Instance of WebLoginController.java
 * 
 * @author Carlos
 * @version
 */
@Slf4j
@EntityScan
@Controller
public class WebController {

	@Autowired
	private IncidentsClient incidentsClient;

	@Autowired
	private AgentsClient agentsClient;
	
	@Autowired
	private OperatorsClient operatorsClient;

	@Autowired
	KafkaService kafkaService;

	@RequestMapping(value = "/")
	public String index() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String setLogin( Model model, @ModelAttribute("UserInfo") UserInfo values,
			BindingResult result, HttpServletResponse response ) {
		String agentId = agentsClient.auth( values.getLogin(),
				values.getPassword(), Integer.parseInt( values.getKind() ) );
		if (agentId != null) {
			Cookie agentSession = new Cookie( "agentId", agentId );
			agentSession.setMaxAge( 1000 );
			response.addCookie( agentSession );

			return "redirect:/incidents";
		}
		return "login";
	}

	@RequestMapping(value = "/incidents", method = RequestMethod.GET)
	public String incidents( Model model, @Nullable @CookieValue("agentId") String agentId,
			@RequestParam(value = "searchtext", required = false) String searchtext ) {
		if (agentId == null)
			return "redirect:/login";

		List<Incident> incidents = Arrays.asList( incidentsClient.findByAgentId( agentId ) );

		if (searchtext != null && !searchtext.isEmpty()) {
			incidents = incidents.stream()
					.filter( i -> i.getTitle().toUpperCase().contains( searchtext.toUpperCase() )
							|| i.getDescription().toUpperCase().contains( searchtext.toUpperCase() )
							|| i.getStatus().toUpperCase().contains( searchtext.toUpperCase() ) )
					.collect( Collectors.toList() );
			// incidents = Lists.reverse( incidents );
			SimpleDateFormat sdf = new SimpleDateFormat( "EEE MMM dd HH:mm:ss Z yyyy",
					new Locale( "us" ) );
			Collections.sort( incidents, Collections.reverseOrder( ( i1, i2 ) -> {
				try {
					return sdf.parse( i1.getDate() ).compareTo( sdf.parse( i2.getDate() ) );
				} catch (ParseException e) {
					log.error( e.getMessage() );
					return 0;
				}
			} ) );
		}

		SimpleDateFormat sdf = new SimpleDateFormat( "EEE MMM dd HH:mm:ss Z yyyy",
				new Locale( "us" ) );
		Collections.sort( incidents, Collections.reverseOrder( ( i1, i2 ) -> {
			try {
				return sdf.parse( i1.getDate() ).compareTo( sdf.parse( i2.getDate() ) );
			} catch (ParseException e) {
				log.error( e.getMessage() );
				return 0;
			}
		} ) );
		model.addAttribute( "incidents", incidents );
		return "incidents";
	}

	@RequestMapping(value = "/incidents/new", method = RequestMethod.POST)
	public String newIncident( @Nullable @CookieValue("agentId") String agentId,
			@ModelAttribute("IncidentInfo") IncidentInfo formValues ) {
		if (agentId == null)
			return "redirect:/login";
		Incident incident = new Incident();
		incident.setTitle( formValues.getTitle() );
		incident.setDescription( formValues.getDescription() );
		incident.setStatus( "OPEN" );
		incident.setLocation( formValues.getLocation() );
		incident.setComments( new Comment[0] );
		incident.setAgentId( agentId );
		incident.setOperatorId( formValues.getOperatorId() );

		// Computing the tags
		String[] tagsList = new String[( (String) formValues.getTags() ).split( "," ).length];
		int i = 0;
		for (String tag : ( (String) formValues.getTags() ).split( "," )) {
			tagsList[i] = ( tag.trim() );
			i++;
		}
		incident.setTags( tagsList );

		// Computing multimedia
		List<String> multimedia = new LinkedList<String>();
		for (String info : ( (String) formValues.getMultimedia() ).split( "," )) {
			multimedia.add( info.trim() );
		}
		String[] multimediaString = multimedia.toArray( new String[multimedia.size()] );
		incident.setMultimedia( multimediaString );

		// Computing properties
		Map<String, String> propsList = new HashMap<String, String>();
		for (String prop : ( (String) formValues.getProperties() ).split( "," )) {
			if (prop.split( ":" ).length == 2)
				propsList.put( prop.split( ":" )[0].trim(), prop.split( ":" )[1].trim() );
		}
		incident.setPropertyVal( propsList );

		log.info( "The resut after the insertion was: "
				+ kafkaService.sendIncidence( incident, "AGENT" ) );

		return "redirect:/incidents";
	}

	@RequestMapping(value = "/incidents/new", method = RequestMethod.GET)
	public String saveIncident( Model model, @Nullable @CookieValue("agentId") String agentId ) {
		if (agentId == null)
			return "redirect:/login";

		model.addAttribute( "agentId", agentId );
		model.addAttribute( "agentName", agentsClient.getAgentName( agentId ) );
		model.addAttribute( "operators", operatorsClient.findAll() );

		return "newIncident";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout( @Nullable @CookieValue("agentId") String agentId,
			HttpServletResponse response ) {
		if (agentId == null)
			return "redirect:/login";
		Cookie agentC = new Cookie( "agentId", agentId );
		agentC.setMaxAge( 0 );
		response.addCookie( agentC );
		return "redirect:/";
	}

	@RequestMapping(value = "/incidents/{id}")
	public String getIncident( Model model, @PathVariable("id") String incidentId,
			@Nullable @CookieValue("agentId") String agentId ) {
		if (agentId == null)
			return "redirect:/login";

		Incident incident = incidentsClient.findByIncidentId( incidentId );
		model.addAttribute( "incident", incident );
		model.addAttribute( "agentName", agentsClient.getAgentName( agentId ) );

		return "incident";
	}
}
