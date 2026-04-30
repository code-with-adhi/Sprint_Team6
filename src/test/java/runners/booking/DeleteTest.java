package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/deleteFeature.feature",
        glue = { "stepdefinitions.delete_booking_stepdef","hooks"},
        plugin = {"pretty","html:target/cucumber-delete-report.html" ,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class DeleteTest extends AbstractTestNGCucumberTests {
}