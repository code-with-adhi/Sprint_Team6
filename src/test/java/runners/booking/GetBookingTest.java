package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/getBookingFeature.feature", 
	glue = { "stepdefinitions.get_booking_stepdef","hooks" },
	plugin = { "pretty", "html:target/cucumber-report.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, 
	monochrome = true)

public class GetBookingTest extends AbstractTestNGCucumberTests {

}
