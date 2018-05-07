package io.github.asw.i3a.agentWebClient.types;

import static org.junit.Assert.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.web.IncidentInfo;
import pl.pojo.tester.api.assertion.Method;

@Category(UnitTest.class)
public class IncidentInfoTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(IncidentInfo.class).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE,
				Method.TO_STRING, Method.CONSTRUCTOR).areWellImplemented();
	}

	@Test
	public void extraTest() {
		IncidentInfo in = new IncidentInfo();
		IncidentInfo in2 = new IncidentInfo();
		assertTrue(in.canEquals(in2));
	}
}
