package io.github.asw.i3a.agentWebClient.types;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.web.UserInfo;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Category(UnitTest.class)
public class UserInfoTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor( UserInfo.class ).testing( Method.GETTER, Method.SETTER )
				.testing( Method.EQUALS )
				.testing( Method.CONSTRUCTOR ).areWellImplemented();
	}
}
