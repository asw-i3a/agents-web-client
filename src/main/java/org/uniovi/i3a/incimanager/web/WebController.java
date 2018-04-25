/*
 * This source file is part of the web-service open source project.
 *
 * Copyright (c) 2018 willy and the web-service project authors.
 * Licensed under GNU General Public License v3.0.
 *
 * See /LICENSE for license information.
 * 
 */
package org.uniovi.i3a.incimanager.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.uniovi.i3a.incimanager.rest.AgentsQueryFormatter;
import org.uniovi.i3a.incimanager.types.Incident;
import org.uniovi.i3a.incimanager.clients.IncidentsServiceClient;
import org.uniovi.i3a.incimanager.clients.IncidentsServiceClientImpl;
import org.uniovi.i3a.incimanager.kafka.KafkaService;
import org.uniovi.i3a.incimanager.rest.AgentsConnection;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

/**
 * Instance of WebLoginController.java
 * 
 * @author
 * @version
 */
@EntityScan
@Controller
public class WebController {

    @Autowired
    AgentsConnection agentsConnection;

    IncidentsServiceClient incidentsClient = new IncidentsServiceClientImpl();

    @Autowired
    KafkaService kafkaService;

    @RequestMapping(value = "/")
    public String index() {
	return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String setLogin(Model model, @ModelAttribute("UserInfo") UserInfo values, BindingResult result,
	    HttpServletResponse response) {
	HttpResponse<JsonNode> authenticationResponse = agentsConnection.executeQuery(
		new AgentsQueryFormatter(values.getLogin(), values.getPassword(), values.getKind()).query());
	if (authenticationResponse.getStatus() == HttpStatus.OK.value()) {
	    model.addAttribute("agentId", authenticationResponse.getBody().getObject().get("agentId"));
	    model.addAttribute("login", values.getLogin());
	    model.addAttribute("password", values.getPassword());

	    Cookie agentId = new Cookie("agentId",
		    (String) authenticationResponse.getBody().getObject().get("agentId"));
	    agentId.setMaxAge(1000);
	    response.addCookie(agentId);

	    return "redirect:/incidents";
	}
	return "login";
    }

    @RequestMapping(value = "/incidents", method = RequestMethod.GET)
    public String incidents(Model model, @Nullable @CookieValue("agentId") String agentId,
	    @RequestParam(value = "searchtext", required = false) String searchtext) {
	if(agentId == null)
	    return "redirect:/login";

	List<Incident> incidents = Arrays.asList(incidentsClient.findByAgentId(agentId));

	if (searchtext != null && !searchtext.isEmpty()) {
	    incidents = incidents.stream()
		    .filter(i -> i.getTitle().toUpperCase().contains(searchtext.toUpperCase())
			    || i.getDescription().toUpperCase().contains(searchtext.toUpperCase())
			    || i.getStatus().toUpperCase().contains(searchtext.toUpperCase()))
		    .collect(Collectors.toList());
	} else {

	}

	model.addAttribute("incidents", incidents);
	return "incidents";
    }

    @RequestMapping(value = "/incidents/new", method = RequestMethod.GET)
    public String newIncident(@Nullable @CookieValue("agentId") String agentId) {
	if(agentId == null)
	    return "redirect:/login";
	return "newIncident";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@Nullable @CookieValue("agentId") String agentId, HttpServletResponse response) {
	if(agentId == null)
	    return "redirect:/login";
	Cookie agentC = new Cookie("agentId",agentId);
	agentC.setMaxAge(0);
	response.addCookie(agentC);
	return "redirect:/";
    }

    @RequestMapping(value = "/incident", method = RequestMethod.POST)
    public String setIncident(Model model, @ModelAttribute("IncidentInfo") IncidentInfo values, BindingResult result) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("incidenceName", values.getName());
	map.put("description", values.getDescription());
	map.put("location", values.getLocation());
	map.put("asignee", values.getAsignee());
	map.put("expiration", values.getExpiration());
	map.put("state", values.getState());

	List<String> tagsList = new LinkedList<String>();
	for (String tag : ((String) values.getTags()).split(",")) {
	    tagsList.add(tag.trim());
	}
	map.put("tags", tagsList);

	List<String> infoList = new LinkedList<String>();
	for (String info : ((String) values.getMultimedia()).split(",")) {
	    infoList.add(info.trim());
	}
	map.put("additional_information", infoList);

	Map<String, String> propsList = new HashMap<String, String>();
	for (String prop : ((String) values.getProperties()).split(",")) {
	    if (prop.split(":").length == 2)
		propsList.put(prop.split(":")[0].trim(), prop.split(":")[1].trim());
	}
	map.put("properties", propsList);

	if (kafkaService.sendIncidence(map)) {
	    model.addAttribute("valuesMap", map);
	    return "result";
	}

	return "incidentForm";
    }

    @RequestMapping(value = "/incident")
    public String getIncident() {
	return "incidentForm";
    }
}
