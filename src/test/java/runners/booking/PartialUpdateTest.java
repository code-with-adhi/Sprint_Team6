package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/partialUpdateFeatue.feature", glue = {
        "stepdefinitions.partial_update_stepdef", "hooks" }, plugin = { "pretty","html:target/cucumber-PartialUpdate-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, monochrome = true)
public class PartialUpdateTest extends AbstractTestNGCucumberTests {
}