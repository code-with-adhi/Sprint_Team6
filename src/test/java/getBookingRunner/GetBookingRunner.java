package getBookingRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/GetFeatureFile.feature",
	    glue = "getBooking",
	    plugin = {"pretty", "html:target/GetBooking_POST_Report.html"},
	    monochrome = true
	)

public class GetBookingRunner extends AbstractTestNGCucumberTests {

}
