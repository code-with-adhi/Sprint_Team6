package GetAllBookingIds.TestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
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

public class GetAll_IdRun {
    
}
