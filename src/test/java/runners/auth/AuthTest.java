package runners.auth;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

// @RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/authFeature.feature",
        glue = {"stepdefinitions.auth_stepdef","hooks"},
        plugin = {
                "pretty","html:target/cucumber-Auth-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class AuthTest extends AbstractTestNGCucumberTests{
}