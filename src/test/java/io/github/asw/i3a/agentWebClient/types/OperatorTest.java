package io.github.asw.i3a.agentWebClient.types;


import org.junit.Test;

import io.github.asw.i3a.agentsWebClient.types.Operator;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class OperatorTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(Operator.class).areWellImplemented();
	}

}
