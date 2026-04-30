package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/createBookingFeature.feature",
	glue = {
		"stepdefinitions.create_booking_stepdef",
		"hooks" },
	plugin = { "pretty","html:target/cucumber-create-report.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
	monochrome = true)
public class CreateBookingTest extends AbstractTestNGCucumberTests {

}
