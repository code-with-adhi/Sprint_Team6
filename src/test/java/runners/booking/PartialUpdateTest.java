package partial_update.partial_update_runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/PartialUpdateFeature/partialUpdateFeatue.feature", 
glue = {"partial_update.partial_update_step_def","Hooks"}, 
plugin = {
                "pretty", "html:target/PartialUpdation-report.html" }, monochrome = true)
public class PartialUpdateTest extends AbstractTestNGCucumberTests {
}