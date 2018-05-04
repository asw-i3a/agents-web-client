package io.github.asw.i3a.agentWebClient.types;

import org.junit.Test;

import io.github.asw.i3a.agentsWebClient.types.Comment;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class CommentTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(Comment.class).areWellImplemented();
	}

}
