package healthcheck.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/healthCheckFeatureFile/healthcheck.feature",
        glue = "healthcheck.stepDefinition",
        plugin = {"pretty", "html:target/healthcheck-cucumber-report.html"},
        monochrome = true
)
public class HealthCheck extends AbstractTestNGCucumberTests {
}