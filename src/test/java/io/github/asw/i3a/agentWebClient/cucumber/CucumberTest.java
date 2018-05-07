package io.github.asw.i3a.agentWebClient.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.asw.i3a.agentsWebClient.Service;

@SpringBootTest(classes = { Service.class })
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources/features" ) //,glue = "cucumbertests.steps"
@ContextConfiguration
public class CucumberTest{

}