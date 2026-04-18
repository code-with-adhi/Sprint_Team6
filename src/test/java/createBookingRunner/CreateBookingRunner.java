package createBookingRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/CreateFeatureFile.feature",
	    glue = "CreateBooking",
	    plugin = {"pretty", "html:target/CreateBooking_POST_Report.html"},
	    monochrome = true
	)
public class CreateBookingRunner extends AbstractTestNGCucumberTests {

}
