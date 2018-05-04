package io.github.asw.i3a.agentWebClient.types;


import org.junit.Test;

import io.github.asw.i3a.agentsWebClient.web.IncidentInfo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class IncidentInfoTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(IncidentInfo.class).areWellImplemented();
	}

}
