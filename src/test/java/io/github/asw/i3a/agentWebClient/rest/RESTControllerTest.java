/*
 * This source file is part of the rest-service open source project.
 *
 * Copyright (c) 2018 willy and the rest-service project authors.
 * Licensed under GNU General Public License v3.0.
 *
 * See /LICENSE for license information.
 * 
 */
package io.github.asw.i3a.agentWebClient.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import TestKit.IntegrationTest;
import io.github.asw.i3a.agentsWebClient.Service;
import net.minidev.json.JSONObject;

/**
 * Instance of RestControllerTest.java
 * 
 * @author Guillermo Facundo Colunga
 * @version
 */
@SpringBootTest(classes = { Service.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Category(IntegrationTest.class)
@ActiveProfiles("test")
public class RESTControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockHttpSession session;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup( this.context ).build();

		session = new MockHttpSession();
	}

	@Test
	public void createIncidentSuccessfulAuth() throws Exception {
		Map<String, Object> payload = new HashMap<String, Object>();

		payload.put( "login", "45170000A" );
		payload.put( "password", "4[[j[frVCUMJ>hU" );
		payload.put( "kind", 1 );
		payload.put( "title", "Fuego en coto carcedo" );
		payload.put( "description", "Hay un fuego que se ha iniciado cerca de la población" );
		payload.put( "location", "10.000, -9.543" );
		String[] tags = { "tag 1", "tag 2" };
		payload.put( "tags", tags );
		String[] multimedia = { "m 1", "m 2" };
		payload.put( "multimedia", multimedia );
		Map<String, String> props = new HashMap<String, String>();
		props.put( "p1", "v1" );
		props.put( "p2", "v2" );
		payload.put( "prop-val", props );
		payload.put( "operatorId", "" );

		MockHttpServletRequestBuilder request = post( "/sensor-feed" ).session( session )
				.contentType( MediaType.APPLICATION_JSON )
				.content( new JSONObject( payload ).toString() );
		System.out.println( new JSONObject( payload ).toString() );
		mockMvc.perform( request ).andExpect( status().isOk() );
	}
	
	@Test
	public void createIncidentUnSuccessfulAuth() throws Exception {
		Map<String, Object> payload = new HashMap<String, Object>();

		payload.put( "login", "pepe" );
		payload.put( "password", "notTheRightPassword" );
		payload.put( "kind", 9 );
		payload.put( "title", "Fuego en coto carcedo" );
		payload.put( "description", "Hay un fuego que se ha iniciado cerca de la población" );
		payload.put( "location", "10.000, -9.543" );
		String[] tags = { "tag 1", "tag 2" };
		payload.put( "tags", tags );
		String[] multimedia = { "m 1", "m 2" };
		payload.put( "multimedia", multimedia );
		Map<String, String> props = new HashMap<String, String>();
		props.put( "p1", "v1" );
		props.put( "p2", "v2" );
		payload.put( "prop-val", props );
		payload.put( "operatorId", "" );

		MockHttpServletRequestBuilder request = post( "/sensor-feed" ).session( session )
				.contentType( MediaType.APPLICATION_JSON )
				.content( new JSONObject( payload ).toString() );
		System.out.println( new JSONObject( payload ).toString() );
		mockMvc.perform( request ).andExpect( status().isUnauthorized() );
	}

}
