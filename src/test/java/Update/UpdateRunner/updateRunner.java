package Update.UpdateRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/resources/UpdateFeature/updateFeature.feature",
 glue = "Update.UpdateStepDefinition", 
 plugin = {"pretty", "html:target/sprint-report.html" }, 
 monochrome = true
 )

public class updateRunner {
}