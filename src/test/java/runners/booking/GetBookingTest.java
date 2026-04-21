package runners.booking;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/GetBookingfeature/GetFeatureFile.feature", glue = { "GetBookingByID",
		"Hooks" }, plugin = { "pretty", "html:target/GetBooking_POST_Report.html" }, monochrome = true)

public class GetBookingTest extends AbstractTestNGCucumberTests {

}
