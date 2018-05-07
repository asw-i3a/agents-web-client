package io.github.asw.i3a.agentWebClient.types;

import static org.junit.Assert.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.web.UserInfo;
import pl.pojo.tester.api.assertion.Method;

@Category(UnitTest.class)
public class UserInfoTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(UserInfo.class).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.CONSTRUCTOR)
				.areWellImplemented();
	}

	@Test
	public void textExtra() {
		UserInfo u = new UserInfo("hola", "holi", "Sensor");
		assertTrue(UserInfo.toJsonFormat(u)
				.equals("{\"login\": \"hola\", \"password\": \"holi\", \"kind\": \"Sensor\", }"));
	}
}
