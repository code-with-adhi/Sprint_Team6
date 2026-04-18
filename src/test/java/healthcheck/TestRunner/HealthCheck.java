package healthcheck.TestRunner;
	import org.junit.runner.RunWith;
	import io.cucumber.junit.Cucumber;
	import io.cucumber.junit.CucumberOptions;

	@RunWith(Cucumber.class)
		@CucumberOptions(
		        features = "src/test/resources/healthCheckFeatureFile/healthcheck.feature",
		        glue = "healthcheck.stepDefinition",
		        plugin = {"pretty", "html:target/healthcheck cucumber-report.html"},
		        monochrome = true
		)

			public class HealthCheck {

		}



