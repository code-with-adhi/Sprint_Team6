package runners.ping;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/healthcheckFeature.feature",
        glue = { "stepdefinitions.health_check_stepdef","hooks"},
        plugin = {"pretty", "html:target/cucumber-HealthCheck-report.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class HealthCheckTest extends AbstractTestNGCucumberTests {
}
