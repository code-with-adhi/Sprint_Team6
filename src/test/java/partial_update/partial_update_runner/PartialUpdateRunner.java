package partial_update.partial_update_runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/PartialUpdateFeature/partialUpdateFeature.feature",
        glue = "partial_update.partial_update_step_def",
        plugin = {"pretty", "html:target/partialUpdation-report.html"},
        monochrome = true
)
public class PartialUpdateRunner {
}