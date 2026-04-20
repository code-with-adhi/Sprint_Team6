package Auth.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

// @RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/AuthFeature/CreateToken.feature",
        glue = {"Auth.StepDefinition","Hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class AuthTest extends AbstractTestNGCucumberTests{
}