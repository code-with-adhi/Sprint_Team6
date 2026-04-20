package GetAllBookingIds.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

// @RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/GetAll_IdFeature/AllBookingIds.feature",
        glue = "GetAllBookingIds.StepDefinition",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)

public class GetAll_IdRun extends AbstractTestNGCucumberTests{
    
}
