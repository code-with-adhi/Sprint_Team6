package delete.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/deletefeaturefile/deleteBooking.feature",
        glue = { "delete.StepDefinition","Hooks"},
        plugin = {"pretty", "html:target/deleteBooking-cucumber-report.html"},
        monochrome = true
)
public class DeleteTest extends AbstractTestNGCucumberTests {
}