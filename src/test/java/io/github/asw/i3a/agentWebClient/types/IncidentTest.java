package io.github.asw.i3a.agentWebClient.types;

import static org.junit.Assert.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import TestKit.UnitTest;
import io.github.asw.i3a.agentsWebClient.types.Comment;
import io.github.asw.i3a.agentsWebClient.types.Incident;
import pl.pojo.tester.api.assertion.Method;

@Category(UnitTest.class)
public class IncidentTest {

	@Test
	public void allPropertiesTest() {
		assertPojoMethodsFor(Incident.class)
				.testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE, Method.CONSTRUCTOR)
				.areWellImplemented();
	}

	@Test
	public void extraTestCanEqual() {
		Incident i = new Incident();
		Incident i2 = new Incident();
		assertTrue(i.canEquals(i2));
	}

	@Test
	public void extraTagsAsString() {
		Incident i = new Incident();
		String[] tags = { "these", "are", "tags" };
		i.setTags(tags);
		assertTrue(i.tagsAsString().equals("[these, are, tags]"));
	}

	@Test
	public void extraMultimediaAsString() {
		Incident i = new Incident();
		String[] multimedia = { "photo.png", "video.mp4" };
		i.setMultimedia(multimedia);
		assertTrue(i.multimediaAsString().equals("[photo.png, video.mp4]"));
	}

	@Test
	public void extraTestGetDate() {
		Incident i = new Incident();
		i.setIncidentId("5aef2acdb1f3911fd0bdb41c");
		assertTrue(i.getDate().equals("Sun May 06 18:18:21 CEST 2018"));
		i.setIncidentId("");
		assertTrue(i.getDate().equals(""));
		i.setIncidentId(null);
		assertTrue(i.getDate().equals(""));
	}

	@Test
	public void extraTestGetComments() {
		Incident in = new Incident();
		Comment first = new Comment();
		first.setComment("primero");
		Comment second = new Comment();
		second.setComment("segundo");
		Comment[] comments = { first, second };
		assertTrue(comments[0].equals(first));
		assertTrue(comments[1].equals(second));
		in.setComments(comments);
		Comment[] result = in.getComments();
		assertTrue(result[0].equals(second));
		assertTrue(result[1].equals(first));
	}

}
