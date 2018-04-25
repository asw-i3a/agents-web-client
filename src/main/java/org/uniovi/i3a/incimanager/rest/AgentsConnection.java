/*
 * This source file is part of the rest-service open source project.
 *
 * Copyright (c) 2018 willy and the rest-service project authors.
 * Licensed under GNU General Public License v3.0.
 *
 * See /LICENSE for license information.
 * 
 */
package org.uniovi.i3a.incimanager.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.extern.slf4j.Slf4j;

/**
 * Instance of AgentsConnection.java
 * 
 * @author
 * @version
 */
@Slf4j
@Service
public class AgentsConnection {

    
    @Autowired
    Environment env;

    @Value("${agents.url}")
    private String service_url;

    public HttpResponse<JsonNode> executeQuery(String query) {
	try {
	    if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("test")) {
		System.out.println("Profile: " + env.getActiveProfiles()[0]);
		log.warn("Entering the fall-back configuration for forfile: " + env.getActiveProfiles()[0]);
		HttpResponse<JsonNode> jsonResponse = Unirest.post(service_url).header("Content-Type", "application/json")
			    .body(query).asJson();
		    return jsonResponse;
	    }
	    
	    String url = "http://asw-i3a-zuul-eu-west-1.guill.io/agents_service/auth";
	    HttpResponse<JsonNode> jsonResponse = Unirest.post(url).header("Content-Type", "application/json")
		    .body(query).asJson();
	    return jsonResponse;
	} catch (UnirestException e) {
	    e.printStackTrace();
	}
	return null;
    }

}
