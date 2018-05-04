package io.github.asw.i3a.agentWebClient.types;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.types.Incident;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Category(UnitTest.class)
public class IncidentTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(Incident.class).testing(Method.GETTER, Method.SETTER)
        .testing(Method.EQUALS)
        .testing(Method.HASH_CODE)
        .testing(Method.CONSTRUCTOR)
        .areWellImplemented();
	}

}
