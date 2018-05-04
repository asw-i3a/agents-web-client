package io.github.asw.i3a.agentWebClient.types;


import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.web.IncidentInfo;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Category(UnitTest.class)
public class IncidentInfoTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(IncidentInfo.class).areWellImplemented();
	}

}
