package io.github.asw.i3a.agentWebClient.types;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.types.Comment;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Category(UnitTest.class)
public class CommentTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(Comment.class).areWellImplemented();
	}

}
