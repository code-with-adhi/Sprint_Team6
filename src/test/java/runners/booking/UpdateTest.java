package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/updateFeature.feature", glue = {
                "stepdefinitions.update_stepdef",
                "hooks" },
                plugin = { "pretty","html:target/cucumber-update-report.html" ,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                }, 
                monochrome = true)
public class UpdateTest extends AbstractTestNGCucumberTests {
}