package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

// @RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/getAllBookingIdsFeature.feature",
        glue = {"stepdefinitions.get_all_ids_stepdef", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)

public class GetAll_IdTest extends AbstractTestNGCucumberTests{
    
}
